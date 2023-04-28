package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rathaur.gpm.DataBaseModal.School;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddAboutSchool extends AppCompatActivity {
   TextInputEditText about,firstname,firstmobile,firstemail,secondname,secondmobile,secondemail;
   TextView submit,openTime,closeTime;
   RelativeLayout open,close;
   Uri imageUri;
    DatabaseReference reference;
    StorageReference sreference;
    FirebaseStorage storage;
    ActivityResultLauncher<String> resultLauncher;
    String iUri="";
    Dialog dialogbox;
    RelativeLayout back;
 ImageView logo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_about_school);
        Objects.requireNonNull(getSupportActionBar()).hide();
        about=findViewById(R.id.about_school);
        open=findViewById(R.id.open_time);
        close=findViewById(R.id.close_time);
        openTime=findViewById(R.id.open_time_text);
        closeTime=findViewById(R.id.close_time_text);
        submit=findViewById(R.id.submit_about_school);
        firstname=findViewById(R.id.contract_details_name);
        firstemail=findViewById(R.id.contract_details_email);
        firstmobile=findViewById(R.id.contract_details_mobile);
        secondname=findViewById(R.id.second_contract_details_name);
        secondemail=findViewById(R.id.second_contract_details_email);
        secondmobile=findViewById(R.id.second_contract_details_mobile);
        logo=findViewById(R.id.about_school_logo_add);
        back=findViewById(R.id.add_about_school_back_pressed);
        storage = FirebaseStorage.getInstance();
        sreference = storage.getReference();
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);

        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
         back.setOnClickListener(view -> onBackPressed());

        open.setOnClickListener(view -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(AddAboutSchool.this, (timePicker, selectedHour, selectedMinute) -> {
                String time=String.format(Locale.getDefault(),"%02d:%02d",selectedHour,selectedMinute);
                openTime.setText( time);
            }, hour, minute, true);
            mTimePicker.show();
        });
        close.setOnClickListener(view -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(AddAboutSchool.this, (timePicker, selectedHour, selectedMinute) -> {
                String time=String.format(Locale.getDefault(),"%02d:%02d",selectedHour,selectedMinute);
                closeTime.setText( time);
            }, hour, minute, true);
            mTimePicker.show();
        });

        submit.setOnClickListener(view -> {

            /// progress dialog
            dialogbox.show();
            reference=FirebaseDatabase.getInstance().getReference("schoolInformation");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String aboutSchool = Objects.requireNonNull(about.getText()).toString();
                    String openSchool = openTime.getText().toString();
                    String closeSchool = closeTime.getText().toString();
                    String name1 = Objects.requireNonNull(firstname.getText()).toString();
                    String email1 = Objects.requireNonNull(firstemail.getText()).toString();
                    String mobile1 = Objects.requireNonNull(firstmobile.getText()).toString();
                    String name2 = Objects.requireNonNull(secondname.getText()).toString();
                    String mobile2 = Objects.requireNonNull(secondmobile.getText()).toString();
                    String email2 = Objects.requireNonNull(secondemail.getText()).toString();
                    School school=new School(aboutSchool, openSchool, closeSchool, name1, mobile1, email1, name2, mobile2, email2,iUri);
                    reference.setValue(school).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            dialogbox.dismiss();
                            dialog.show();
                            TextView ok=dialog.findViewById(R.id.success_dialog_button);
                            ok.setOnClickListener(view1 -> onBackPressed());
                        }
                    }).addOnFailureListener(e -> {
                        dialogbox.dismiss();
                        dialog.dismiss();
                        Toast.makeText(AddAboutSchool.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    dialogbox.dismiss();
                    dialog.dismiss();
                    Toast.makeText(AddAboutSchool.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        resultLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

            if (result!=null){
                imageUri=result;
                uplodeImage();
            }
        });
        logo.setOnClickListener(view -> resultLauncher.launch("image/*"));
    }

        private void uplodeImage() {
            StorageReference storageReference = sreference.child("School/"+"logo");
            storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                logo.setImageURI(imageUri);
                dialogbox.dismiss();
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> iUri= String.valueOf(uri));
            }).addOnProgressListener(snapshot -> dialogbox.show());
        }
}
package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rathaur.gpm.DataBaseModal.PDF;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddStudyMaterials extends AppCompatActivity {
    Dialog dialogbox;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    TextView select;
    String url="";
    ActivityResultLauncher<String> pdf;
    TextView button;
    TextInputEditText editText;
    RelativeLayout back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study_materials);
        Objects.requireNonNull(getSupportActionBar()).hide();
        select=findViewById(R.id.select_pdf);
        editText=findViewById(R.id.student_add_pdf_edittext);
        button=findViewById(R.id.student_add_study);
        back=findViewById(R.id.student_add_study_back_pressed);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("studyMaterials");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        pdf=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result!=null){
                    uploadFile(result);
                }
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdf.launch("application/pdf");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!url.isEmpty()){
                    if (!Objects.requireNonNull(editText.getText()).toString().trim().isEmpty()){
                        dialogbox.show();
                        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                        String file=editText.getText().toString();
                        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                        String enrollment=sharedPreferences.getString("enrollment","");
                        PDF pdf=new PDF(file,currentTime,currentDate,enrollment,url);
                        databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(pdf).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               dialogbox.dismiss();
                                dialog.show();
                                TextView ok=dialog.findViewById(R.id.success_dialog_button);
                                TextView text=dialog.findViewById(R.id.success_dialog_text);
                                text.setText("Study Materials successfully saved");
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                    }else {
                        Toast.makeText(AddStudyMaterials.this, "enter file name", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(AddStudyMaterials.this, "upload pdf", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadFile(Uri data) {
       dialogbox.show();
        StorageReference reference=storageReference.child("study/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialogbox.dismiss();
                        url=uri.toString();
                    }

                });
            }
        });
    }
}
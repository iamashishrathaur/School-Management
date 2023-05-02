package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.AllotSubjectModal;
import com.rathaur.gpm.DataBaseModal.Teacher;
import com.rathaur.gpm.databinding.ActivityTeacherAllotSubjectBinding;

import java.util.ArrayList;
import java.util.Objects;

public class TeacherAllotSubject extends AppCompatActivity {

    ActivityTeacherAllotSubjectBinding binding;

    ArrayList<String> list;
    DatabaseReference databaseReference;
    Dialog dialog;
    ArrayList<String> arrayList;
    String resultSubject;
    String resultTeacher;
    String enrollmentTeacher;
    Dialog pdialog,sdialog;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTeacherAllotSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        pdialog=new Dialog(this);
        sdialog=new Dialog(this);
        pdialog.setContentView(R.layout.progress_dialog);
        sdialog.setContentView(R.layout.success_dialog);
        Objects.requireNonNull(pdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        Objects.requireNonNull(sdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        pdialog.setCanceledOnTouchOutside(false);
        sdialog.setCanceledOnTouchOutside(false);
        TextView textView=sdialog.findViewById(R.id.success_dialog_text);
        textView.setText("Subject successfully saved");

        binding.subjectAllotBackPressed.setOnClickListener(view ->onBackPressed());
        databaseReference= FirebaseDatabase.getInstance().getReference("teacher");

        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        Objects.requireNonNull(dialog.getWindow()).setLayout(650,800);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        list=new ArrayList<>();
        arrayList=new ArrayList<>();
        arrayList.add("Communication Skills–I");
        arrayList.add("Applied Mathematics-I");
        arrayList.add("Applied Physics-I");
        arrayList.add("Applied Chemistry");
        arrayList.add("F.C.I.T.");
        arrayList.add("Technical Drawing");
        arrayList.add("Workshop Practice");
        arrayList.add("Applied Mathematics-II");
        arrayList.add("Applied Physics-II");
        arrayList.add("B.E. And E.E");
        arrayList.add("Concept of Programing C");
        arrayList.add("Office Automation Tools");
        arrayList.add("Applied Mathematics-III");
        arrayList.add("Internet and Web technology");
        arrayList.add("Environment Studies");
        arrayList.add("Data Communication and Computer Networks");
        arrayList.add("Data Structure Using C");
        arrayList.add("Digital Electronics");
        arrayList.add("Communication Skills–II");
        arrayList.add("Database Management System");
        arrayList.add("OOPs Java");
        arrayList.add("Operating System");
        arrayList.add("E-Commerce And Digital Marketing");
        arrayList.add("Energy Conservation");
        arrayList.add("Universal Human Values");
        arrayList.add("Software Engineering");
        arrayList.add("Web Development Using Php");
        arrayList.add("Computer Programing Using Python");
        arrayList.add("C.A.H.M.");
        arrayList.add("Internet Of Things");
        arrayList.add("Minor Project Work");
        arrayList.add("Development of Android Application");
        arrayList.add("Cloud Computing");
        arrayList.add("IM And Ed");
        arrayList.add("Advance Java & .Net & DS And ML");
        arrayList.add("Asp .Net");
        arrayList.add("DS And ML");
        arrayList.add("Major Project Work");



        binding.searchAllotSubject.setOnClickListener(v -> {
            dialog.show();
            EditText editText=dialog.findViewById(R.id.search_dialog_edit_text);
            ListView listView=dialog.findViewById(R.id.search_dialog_list_view);
            ArrayAdapter<String> adapter=new ArrayAdapter<>(TeacherAllotSubject.this, android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(adapter);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });
            listView.setOnItemClickListener((parent, view, position, id) -> {
                binding.searchAllotSubject.setText(adapter.getItem(position));
                resultSubject=adapter.getItem(position);
                dialog.dismiss();
            });
        });
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.chooseAllotTeacher.setAdapter(adapter);
        binding.chooseAllotTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              String selectedValue=list.get(position);
              compare(selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Teacher teacher=dataSnapshot.getValue(Teacher.class);
                        String name= Objects.requireNonNull(teacher).getTname();
                        list.add(name);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherAllotSubject.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.allotSubjectSubmit.setOnClickListener(v -> {
            if (!resultTeacher.isEmpty()){
                if (!resultSubject.isEmpty()){
                    pdialog.show();
                        DatabaseReference db=FirebaseDatabase.getInstance().getReference("allotsubject");
                        AllotSubjectModal modal=new AllotSubjectModal(resultTeacher,enrollmentTeacher,resultSubject);
                        db.orderByChild("subject").equalTo(resultSubject).addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (!snapshot.exists()){
                                    db.push().setValue(modal).addOnSuccessListener(unused -> {
                                        pdialog.dismiss();
                                        sdialog.show();
                                        TextView button=sdialog.findViewById(R.id.success_dialog_button);
                                        button.setOnClickListener(v1 ->onBackPressed());
                                    });
                                }
                                else {
                                    pdialog.dismiss();
                                    Snackbar.make(v,"subject is already exist",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                pdialog.dismiss();
                                Toast.makeText(TeacherAllotSubject.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                }
                else {
                    Toast.makeText(TeacherAllotSubject.this, "please select subject", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(TeacherAllotSubject.this, "please select teacher", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void compare(String selectedValue){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   Teacher teacher=dataSnapshot.getValue(Teacher.class);
                   if (Objects.equals(Objects.requireNonNull(teacher).getTname(), selectedValue)){
                      resultTeacher=teacher.getTname();
                      enrollmentTeacher=teacher.getTenrollment();
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherAllotSubject.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        pdialog.dismiss();
        sdialog.dismiss();
    }
}
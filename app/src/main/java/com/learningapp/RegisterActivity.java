package com.learningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText email,password,uName,fName;
    MaterialCardView signupBtn;
    ProgressDialog pd;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        uName = findViewById(R.id.uName);
        fName = findViewById(R.id.fName);
        signupBtn = findViewById(R.id.signupBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userName = uName.getText().toString().trim();
                String userFullName = fName.getText().toString().trim();

                if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userFullName)){
                    email.setError("Email cannot be blank");
                    password.setError("Password cannot be blank");
                    uName.setError("Username cannot be blank");
                    fName.setError("Full name cannot be blank");
                }else if (userName.contains(".")|| userName.contains(" ")){
                    uName.setError("Please Create a proper username");
                } else{
                    pd.setMessage("Signing up...");
                    pd.show();
                    register(userEmail,userPassword,userName,userFullName);
                }
            }
        });
    }

    private void register(String userEmail, String userPassword, String userName, String userFullName) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid());
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("name",userFullName);
                hashMap.put("email",userEmail);
                hashMap.put("username",userName);
                hashMap.put("password",userPassword);
                reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Username").child(userName);
                    HashMap<String,String> hashMap1 = new HashMap<>();
                    hashMap1.put("uid",firebaseAuth.getCurrentUser().getUid());
                    reference1.setValue(hashMap1).addOnCompleteListener(task2 -> {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                        Intent main = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(main);
                        RegisterActivity.this.finish();
                    });
                });
            }
        });
    }
}
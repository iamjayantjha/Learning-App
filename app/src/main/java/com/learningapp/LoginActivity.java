package com.learningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    EditText email,password;
    MaterialCardView loginBtn;
    String userEmail, userPassword;

    TextView signUp,forgotPassword;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        forgotPassword = findViewById(R.id.forgotPassword);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.signUp);
        pd = new ProgressDialog(this);
        loginBtn.setOnClickListener(v -> {
            userEmail = email.getText().toString().trim();
            userPassword = password.getText().toString().trim();
            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)){
                email.setError("Email cannot be blank");
                password.setError("Password cannot be blank");
            }else {
                pd.setMessage("Logging in...");
                pd.show();
               signIn(userEmail,userPassword);
               loginBtn.setEnabled(false);
               loginBtn.setCardBackgroundColor(getResources().getColor(R.color.primary_disabled));
            }
        });
        signUp.setOnClickListener(v -> {
            Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(register);
        });
        forgotPassword.setOnClickListener(v -> {
            userEmail = email.getText().toString().trim();
            if (TextUtils.isEmpty(userEmail)) {
                email.setError("Please enter the email for resetting password");
            }else {
                pd.setMessage("Sending reset link...");
                pd.show();
                firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Reset link sent to your email",Toast.LENGTH_SHORT).show();
                    }else {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Error sending reset link",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void signIn(String userEmail, String userPassword) {
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                Intent main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(main);
                LoginActivity.this.finish();
            }else {
                Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();
                loginBtn.setEnabled(true);
                loginBtn.setCardBackgroundColor(getResources().getColor(R.color.primary));
            }
            pd.dismiss();
        });
    }
}
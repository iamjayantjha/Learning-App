package com.learningapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.learningapp.Modal.Username;
import com.learningapp.Modal.Users;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    CircleImageView userProfile;
    TextInputEditText fullName, userName, bio, email;
    TextView changeBtn,changeProfilePhoto;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ImageView close;
    Uri imageUri;
    String myUrl = "",oldUsername="";
    StorageTask uploadTask;
    StorageReference storageReference;
    String url="";
    boolean isUsernameChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userProfile = findViewById(R.id.userProfile);
        fullName = findViewById(R.id.fullName);
        userName = findViewById(R.id.userName);
        changeProfilePhoto = findViewById(R.id.changeProfilePhoto);
        bio = findViewById(R.id.bio);
        close = findViewById(R.id.close);
        email = findViewById(R.id.email);
        changeBtn = findViewById(R.id.changeBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("profile_pic");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                assert user != null;
                fullName.setText(user.getName());
                userName.setText(user.getUsername());
                oldUsername = user.getUsername();
                bio.setText(user.getBio());
                email.setText(user.getEmail());
                url = user.getImage_url();
                Glide.with(getApplicationContext()).load(user.getImage_url()).into(userProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Objects.requireNonNull(userName.getText()).toString().equals("")){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Username").child(s.toString());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Username username = snapshot.getValue(Username.class);
                                assert username != null;
                                if(!username.getUid().equals(firebaseUser.getUid())){
                                    userName.setError("Username already exists");
                                    changeBtn.setVisibility(View.GONE);
                                }else if(userName.getText().toString().equals(oldUsername)){
                                    changeBtn.setTextColor(getResources().getColor(R.color.black));

                                }
                            }else {
                                isUsernameChanged = true;
                                changeBtn.setVisibility(View.VISIBLE);
                                changeBtn.setTextColor(getResources().getColor(R.color.primary));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    changeBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        changeProfilePhoto.setOnClickListener(v -> {
            choosePicture();
        });

        close.setOnClickListener(v -> {
              onBackPressed();
            });

        changeBtn.setOnClickListener(v -> {
            updateProfile();
        });
    }

    private void updateProfile() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("bio",bio.getText().toString().trim());
        hashMap.put("email",email.getText().toString().trim());
        hashMap.put("id",firebaseUser.getUid());
        hashMap.put("image_url",url);
        hashMap.put("name",fullName.getText().toString().trim());
        hashMap.put("username",userName.getText().toString().trim());
        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if (isUsernameChanged){
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Username").child(oldUsername);
                        reference1.removeValue();
                        reference1 = FirebaseDatabase.getInstance().getReference("Username").child(userName.getText().toString().trim());
                        HashMap<String,String> hashMap1 = new HashMap<>();
                        hashMap1.put("uid",firebaseUser.getUid());
                        reference1.setValue(hashMap1).addOnCompleteListener(task1 -> {
                            onBackPressed();
                        });
                    }
                }else {
                    Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            userProfile.setImageURI(imageUri);
            uploadImage();

        }
    }

    private String getFileExtension(Uri uri){
        return uri.getPath().substring(uri.getPath().lastIndexOf("."));
    }

    private void uploadImage() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading your profile picture");
        pd.show();
        pd.setCancelable(false);
        if(imageUri!=null){
            StorageReference storageReference1 = storageReference.child(firebaseUser.getUid());
            uploadTask = storageReference1.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isComplete()){
                        throw task.getException();
                    }
                    return storageReference1.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = (Uri) task.getResult();
                        myUrl = downloadUri.toString();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("bio",bio.getText().toString().trim());
                        hashMap.put("email",email.getText().toString().trim());
                        hashMap.put("id",firebaseUser.getUid());
                        hashMap.put("image_url",myUrl);
                        hashMap.put("name",fullName.getText().toString().trim());
                        hashMap.put("username",userName.getText().toString().trim());
                        reference.setValue(hashMap).addOnCompleteListener(task1 -> {
                            if (isUsernameChanged){
                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Username").child(oldUsername);
                                reference1.removeValue();
                                reference1 = FirebaseDatabase.getInstance().getReference("Username").child(userName.getText().toString().trim());
                                HashMap<String,String> hashMap1 = new HashMap<>();
                                hashMap1.put("uid",firebaseUser.getUid());
                                reference1.setValue(hashMap1).addOnCompleteListener(task2 -> {
                                    pd.dismiss();
                                    onBackPressed();
                                });
                            }else {
                                pd.dismiss();
                                onBackPressed();
                            }
                        });
                    }else {
                        pd.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(EditProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            pd.dismiss();
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
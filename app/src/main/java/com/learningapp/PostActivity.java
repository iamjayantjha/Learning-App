package com.learningapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {
    ImageView close,userPost;
    Uri imageUri;
    String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;
    TextView post;
    EditText caption, location;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        close = findViewById(R.id.close);
        userPost = findViewById(R.id.userPost);
        post = findViewById(R.id.post);
        caption = findViewById(R.id.caption);
        location = findViewById(R.id.location);
        firebaseAuth = FirebaseAuth.getInstance();
        close.setOnClickListener(v -> onBackPressed());
        storageReference = FirebaseStorage.getInstance().getReference("posts");
        CropImage.activity()
                .setAspectRatio(1,1)
                .start(PostActivity.this);

        post.setOnClickListener(v -> {
            uploadImage();
        });

    }

    private void uploadImage() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading your post");
        pd.show();
        pd.setCancelable(false);
        if(imageUri!=null){
            StorageReference storageReference1 = storageReference.child(System.currentTimeMillis()+""+getFileExtension(imageUri));
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

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
                        String postId = reference.push().getKey();
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("postId",postId);
                        hashMap.put("postImage",myUrl);
                        hashMap.put("caption",caption.getText().toString());
                        hashMap.put("date","");
                        hashMap.put("publisher", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                        hashMap.put("location",location.getText().toString());
                        assert postId != null;
                        reference.child(postId).setValue(hashMap);
                        pd.dismiss();
                        onBackPressed();
                    }else {
                        pd.dismiss();
                        Toast.makeText(PostActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(PostActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            pd.dismiss();
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri uri){
        return uri.getPath().substring(uri.getPath().lastIndexOf("."));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result= CropImage.getActivityResult(data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            imageUri = result.getUri();
            userPost.setImageURI(imageUri);
        }
        else{
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_animation,R.anim.slide_down);
    }
}
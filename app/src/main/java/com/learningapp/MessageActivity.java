package com.learningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Adapter.MessageAdapter;
import com.learningapp.Modal.Chats;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    CircleImageView userProfilePicture;
    TextView userName,name,sendBtn;
    String userID;
    ImageView back;
    RecyclerView recyclerView;
    List<Chats> chatsList;
    EditText message;
    MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        userID = getIntent().getStringExtra("userid");
        userProfilePicture = findViewById(R.id.userProfilePicture);
        userName = findViewById(R.id.userName);
        message = findViewById(R.id.message);
        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setHasFixedSize(true);
        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(v -> {
            String msg = message.getText().toString();
            if (!msg.equals("")){
                sendMessage(msg);
                message.setText("");
            }else {
                Toast.makeText(this, "Can't send an empty message", Toast.LENGTH_SHORT).show();
            }
        });
        chatsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        back.setOnClickListener(v -> {
            startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        });
        getUserInfo();
        readMessages();
    }

    private void getUserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
                userName.setText(Objects.requireNonNull(snapshot.child("username").getValue()).toString());
                Glide.with(getApplicationContext()).load(Objects.requireNonNull(snapshot.child("image_url").getValue()).toString()).into(userProfilePicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message){
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        String ampm = "AM";
        if (hours>12){
            hours = hours-12;
            ampm = "PM";
        }
        String time = hours+":"+minutes+" "+ampm;
        String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("sender",myUID);
        hashMap.put("receiver",userID);
        hashMap.put("message",message);
        hashMap.put("time",time);
        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessages(){
        String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Chats chats = dataSnapshot.getValue(Chats.class);
                        assert chats != null;
                                if (chats.getReceiver().equals(myUID) && chats.getSender().equals(userID) ||
                                chats.getReceiver().equals(userID) && chats.getSender().equals(myUID)){
                                    chatsList.add(chats);
                                }
                    }
                }
                messageAdapter = new MessageAdapter(MessageActivity.this,chatsList);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
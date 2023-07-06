package com.learningapp.DirectMessage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Home.HomeFragment;
import com.learningapp.R;

import java.util.Objects;


public class MessageFragment extends Fragment {
    ImageView back;
    TextView userName;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        back = view.findViewById(R.id.back);
        userName = view.findViewById(R.id.userName);
        getUserInfo();
        back.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit());
        return view;
    }

    private void getUserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                userName.setText(Objects.requireNonNull(snapshot.child("username").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
    }
}
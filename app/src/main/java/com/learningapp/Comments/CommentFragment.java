package com.learningapp.Comments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Adapter.CommentsAdapter;
import com.learningapp.Home.HomeFragment;
import com.learningapp.Modal.Comments;
import com.learningapp.Modal.Users;
import com.learningapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentFragment extends Fragment {
    RecyclerView recyclerView;
    CircleImageView profilePicture;
    EditText comment;
    TextView postComment;
    List<Comments> commentsList;
    CommentsAdapter commentsAdapter;
    String postId;
    FirebaseAuth firebaseAuth;
    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        SharedPreferences preferences = requireActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        postId = preferences.getString("postId", "");
        back = view.findViewById(R.id.back);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);
        profilePicture = view.findViewById(R.id.profilePicture);
        comment = view.findViewById(R.id.comment);
        postComment = view.findViewById(R.id.postComment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        commentsList = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(getContext(), commentsList);
        recyclerView.setAdapter(commentsAdapter);
        postComment.setOnClickListener(v ->{
            if (comment.getText().toString().equals("")) {
                Toast.makeText(getContext(), "You can't send empty comment", Toast.LENGTH_SHORT).show();
            } else {
                addComment();
            }
        });
        back.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
        });
        getUserInfo();
        readComments();
        return view;
    }

    private void addComment() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postId);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("comment", comment.getText().toString().trim());
        hashMap.put("publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        reference.setValue(hashMap);
        comment.setText("");
    }

    private void getUserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                assert user != null;
                Glide.with(requireActivity()).load(user.getImage_url()).into(profilePicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readComments() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentsList.clear();
               if (snapshot.exists()){
                   Comments comments = snapshot.getValue(Comments.class);
                   commentsList.add(comments);
               }
                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
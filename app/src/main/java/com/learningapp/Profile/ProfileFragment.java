package com.learningapp.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Adapter.PostGridAdapter;
import com.learningapp.EditProfileActivity;
import com.learningapp.LoginActivity;
import com.learningapp.MessageActivity;
import com.learningapp.Modal.PostGrid;
import com.learningapp.Modal.Posts;
import com.learningapp.Modal.Users;
import com.learningapp.R;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    ImageView settings;
    FirebaseAuth firebaseAuth;
    String userId;
    FirebaseUser firebaseUser;
    TextView userName,name,bio,edit_profile,postCount,followersCount,followingCount,notPosted;
    CircleImageView userProfilePicture;

    MaterialCardView editProfile,dm;
    GridView postGrid;
    ArrayList<PostGrid> postGrids;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences = requireActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = preferences.getString("profileid", firebaseUser.getUid());
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        postGrids = new ArrayList<>();
        notPosted = view.findViewById(R.id.notPosted);
        dm = view.findViewById(R.id.dm);
        postGrid = view.findViewById(R.id.postGrid);
        postCount = view.findViewById(R.id.postCount);
        followersCount = view.findViewById(R.id.followersCount);
        followingCount = view.findViewById(R.id.followingCount);
        edit_profile = view.findViewById(R.id.edit_profile);
        editProfile = view.findViewById(R.id.editProfile);
        settings = view.findViewById(R.id.settings);
        userName = view.findViewById(R.id.userName);
        name = view.findViewById(R.id.name);
        bio = view.findViewById(R.id.bio);
        userProfilePicture = view.findViewById(R.id.userProfilePicture);
        if (userId.equals(firebaseUser.getUid())) {
            settings.setVisibility(View.VISIBLE);
        } else {
            settings.setVisibility(View.GONE);
        }
        settings.setOnClickListener(v -> {
            firebaseAuth.signOut();
            Intent login = new Intent(getActivity(), LoginActivity.class);
            startActivity(login);
            requireActivity().finish();
        });
        dm.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MessageActivity.class);
            intent.putExtra("userid",userId);
            startActivity(intent);
            ProfileFragment.this.requireActivity().finish();
        });
        editProfile.setOnClickListener(v -> {
            if (userId.equals(firebaseUser.getUid())){
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }else {
                if (edit_profile.getText().toString().equals("Follow")) {
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid()).child("following").child(userId).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("follow").child(userId).child("followers").child(firebaseUser.getUid()).setValue(true);
                    edit_profile.setText("Following");
                    edit_profile.setTextColor(getResources().getColor(R.color.black));
                    editProfile.setCardBackgroundColor(getResources().getColor(R.color.text_disabled));
                    dm.setVisibility(View.VISIBLE);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid()).child("following").child(userId).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("follow").child(userId).child("followers").child(firebaseUser.getUid()).removeValue();
                    edit_profile.setText("Follow");
                    edit_profile.setTextColor(getResources().getColor(R.color.white));
                    editProfile.setCardBackgroundColor(getResources().getColor(R.color.primary));
                    dm.setVisibility(View.GONE);
                }
            }
        });
        if (userId.equals(firebaseUser.getUid())) {
            edit_profile.setText("Edit Profile");
        } else {
            doesUserFollow(userId);
        }
        getUserInfo();
        getFollowers();
        getFollowing();
        getPostCount();
        return view;
    }

    private void getPostCount(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Posts posts = dataSnapshot.getValue(Posts.class);
                    assert posts != null;
                    if (posts.getPublisher().equals(userId)){
                        i++;
                        postGrids.add(new PostGrid(posts.getPostImage()));
                    }
                }
                if (i==0){
                    postGrid.setVisibility(View.GONE);
                    notPosted.setVisibility(View.VISIBLE);
                }
                postCount.setText(String.valueOf(i));
                PostGridAdapter postGridAdapter = new PostGridAdapter(requireActivity(),postGrids);
                postGrid.setAdapter(postGridAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getFollowers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("follow").child(userId).child("followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followersCount.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getFollowing(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("follow").child(userId).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followingCount.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void doesUserFollow(String userId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("follow").child(firebaseUser.getUid()).child("following");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userId).exists()) {
                    edit_profile.setText("Following");
                    edit_profile.setTextColor(getResources().getColor(R.color.black));
                    editProfile.setCardBackgroundColor(getResources().getColor(R.color.text_disabled));
                    dm.setVisibility(View.VISIBLE);
                } else {
                    edit_profile.setText("Follow");
                    edit_profile.setTextColor(getResources().getColor(R.color.white));
                    editProfile.setCardBackgroundColor(getResources().getColor(R.color.primary));
                    dm.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserInfo(){
        DatabaseReference reference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                if (user != null) {
                    userName.setText(user.getUsername());
                    Glide.with(requireActivity()).load(user.getImage_url()).into(userProfilePicture);
                    name.setText(user.getName());
                    bio.setText(user.getBio());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
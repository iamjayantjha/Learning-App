package com.learningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Home.HomeFragment;
import com.learningapp.Modal.Users;
import com.learningapp.Profile.ProfileFragment;
import com.learningapp.Reels.ReelsFragment;
import com.learningapp.Search.SearchFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    ImageView home,search,add,reels;
    CircleImageView profile;
    FrameLayout fragmentContainer;
    Fragment selectedFragment = null;
    FirebaseAuth userAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        add = findViewById(R.id.add);
        reels = findViewById(R.id.reels);
        profile = findViewById(R.id.profile);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        userAuth = FirebaseAuth.getInstance();
        currentUser = userAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null){
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            MainActivity.this.finish();
        }else{
            home.setOnClickListener(v -> {
                selectedFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
                home.setImageResource(R.drawable.ic_home_filled);
                search.setImageResource(R.drawable.ic_search_outlined);
                reels.setImageResource(R.drawable.ic_reels_outlined);
            });

            search.setOnClickListener(v -> {
                selectedFragment = new SearchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
                home.setImageResource(R.drawable.ic_home_outlined);
                search.setImageResource(R.drawable.ic_search_filled);
                reels.setImageResource(R.drawable.ic_reels_outlined);
            });

            reels.setOnClickListener(v -> {
                selectedFragment = new ReelsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
                home.setImageResource(R.drawable.ic_home_outlined);
                search.setImageResource(R.drawable.ic_search_outlined);
                reels.setImageResource(R.drawable.ic_reels_filled);
            });

            profile.setOnClickListener(v -> {
                selectedFragment = new ProfileFragment();
                SharedPreferences sharedPreferences = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profileid", currentUser.getUid());
                editor.apply();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
                home.setImageResource(R.drawable.ic_home_outlined);
                search.setImageResource(R.drawable.ic_search_outlined);
                reels.setImageResource(R.drawable.ic_reels_outlined);
            });

            add.setOnClickListener(v -> {
                Intent post = new Intent(MainActivity.this, PostContentActivity.class);
                startActivity(post);
                overridePendingTransition(R.anim.no_animation,R.anim.slide_up);
            });

            if (selectedFragment == null){
                selectedFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
                home.setImageResource(R.drawable.ic_home_filled);
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            }
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        Glide.with(MainActivity.this).load(user.getImage_url()).into(profile);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
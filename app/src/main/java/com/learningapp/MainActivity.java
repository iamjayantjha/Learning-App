package com.learningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.learningapp.Home.HomeFragment;
import com.learningapp.Profile.ProfileFragment;
import com.learningapp.Reels.ReelsFragment;
import com.learningapp.Search.SearchFragment;

public class MainActivity extends AppCompatActivity {
    ImageView home,search,add,reels,profile;
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
        home.setOnClickListener(v -> {
            selectedFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            home.setImageResource(R.drawable.ic_home_filled);
            search.setImageResource(R.drawable.ic_search_outlined);
            reels.setImageResource(R.drawable.ic_reels_outlined);
            profile.setImageResource(R.drawable.ic_profile_outline);
        });

        search.setOnClickListener(v -> {
            selectedFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            home.setImageResource(R.drawable.ic_home_outlined);
            search.setImageResource(R.drawable.ic_search_filled);
            reels.setImageResource(R.drawable.ic_reels_outlined);
            profile.setImageResource(R.drawable.ic_profile_outline);
        });

        reels.setOnClickListener(v -> {
            selectedFragment = new ReelsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            home.setImageResource(R.drawable.ic_home_outlined);
            search.setImageResource(R.drawable.ic_search_outlined);
            reels.setImageResource(R.drawable.ic_reels_filled);
            profile.setImageResource(R.drawable.ic_profile_outline);
        });

        profile.setOnClickListener(v -> {
            selectedFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            home.setImageResource(R.drawable.ic_home_outlined);
            search.setImageResource(R.drawable.ic_search_outlined);
            reels.setImageResource(R.drawable.ic_reels_outlined);
            profile.setImageResource(R.drawable.ic_profile_filled);
        });
        if (selectedFragment == null){
            selectedFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            home.setImageResource(R.drawable.ic_home_filled);
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null){
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            MainActivity.this.finish();
        }
    }
}
package com.learningapp.Reels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Adapter.VideoAdapter;
import com.learningapp.Modal.VideoAct;
import com.learningapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReelsFragment extends Fragment {

    ViewPager2 viewPager;
    List<VideoAct> videoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireContext().setTheme(R.style.reelsTheme);
        View view = inflater.inflate(R.layout.fragment_reels, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        videoList = new ArrayList<>();
        getReels();
        return view;
    }

    private void getReels() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Reels").child("public");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    videoList.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        VideoAct video = dataSnapshot.getValue(VideoAct.class);
                        videoList.add(video);
                    }
                    VideoAdapter videoAdapter = new VideoAdapter(videoList, getContext());
                    viewPager.setAdapter(videoAdapter);
                }else {
                    Toast.makeText(getContext(), "No reels uploaded yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
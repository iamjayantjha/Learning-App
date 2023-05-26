package com.learningapp.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView username, follow_text;
    public CircleImageView user_img;
    public MaterialCardView follow_btn;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.username);
        user_img = itemView.findViewById(R.id.user_img);
        follow_btn = itemView.findViewById(R.id.follow_btn);
        follow_text = itemView.findViewById(R.id.follow_text);


    }

    public void isFollowing(final String userID, final MaterialCardView button){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userID).exists()){
                    follow_text.setText("Following");
                    button.setBackgroundColor(button.getContext().getResources().getColor(R.color.text_disabled));
                    button.setStrokeColor(button.getContext().getResources().getColor(R.color.stroke));
                    button.setStrokeWidth(1);
                    button.setRadius(10);
                }else {
                    follow_text.setText("Follow");
                    button.setBackgroundColor(button.getContext().getResources().getColor(R.color.primary));
                    button.setStrokeWidth(0);
                    button.setRadius(10);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
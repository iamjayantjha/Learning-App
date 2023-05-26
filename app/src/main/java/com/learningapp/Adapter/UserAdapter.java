package com.learningapp.Adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.learningapp.Modal.Users;
import com.learningapp.Profile.ProfileFragment;
import com.learningapp.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<ViewHolder>{
    private Context mContext;
    private List<Users> mUsers;
    private FirebaseUser firebaseUser;

    public UserAdapter(Context context, List<Users> mUsers) {
        this.mContext = context;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Users user = mUsers.get(position);
        holder.follow_btn.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        Glide.with(mContext).load(user.getImage_url()).into(holder.user_img);
        holder.isFollowing(user.getId(), holder.follow_btn);

        if (user.getId().equals(firebaseUser.getUid())) {
            holder.follow_btn.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profileid", user.getId());
            editor.apply();
            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();

        });

        holder.follow_btn.setOnClickListener(v -> {
            if (holder.follow_text.getText().toString().equalsIgnoreCase("follow")){
                FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid()).child("following").child(user.getId()).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("follow").child(user.getId()).child("followers").child(firebaseUser.getUid()).setValue(true);
            }else {
                FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getUid()).child("following").child(user.getId()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("follow").child(user.getId()).child("followers").child(firebaseUser.getUid()).removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
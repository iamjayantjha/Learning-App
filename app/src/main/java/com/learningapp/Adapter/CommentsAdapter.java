package com.learningapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.Modal.Comments;
import com.learningapp.Modal.Users;
import com.learningapp.Profile.ProfileFragment;
import com.learningapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{
    public Context mContext;
    public List<Comments> comments;
    public FirebaseUser firebaseUser;

    public CommentsAdapter(Context mContext, List<Comments> comments) {
        this.mContext = mContext;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((mContext)).inflate(R.layout.comment_item, parent, false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Comments comment = comments.get(position);
        holder.userComment.setText(comment.getComment());
        getUserInfo(holder.profileImage, holder.userName, comment.getPublisher());
        holder.profileImage.setOnClickListener(v -> {
            SharedPreferences.Editor preferences = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
            preferences.putString("profileid", comment.getPublisher());
            preferences.apply();
            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfileFragment()).commit();
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    private void getUserInfo(final CircleImageView profileImage, final TextView userName, String publisherId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(publisherId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                assert user != null;
                userName.setText(user.getUsername());
                Glide.with(mContext).load(user.getImage_url()).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView userName, userComment;
        public ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.userName);
            userComment = itemView.findViewById(R.id.userComment);
        }
    }
}

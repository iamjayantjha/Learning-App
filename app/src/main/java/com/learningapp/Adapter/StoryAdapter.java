package com.learningapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learningapp.AddStoryActivity;
import com.learningapp.Modal.Story;
import com.learningapp.Modal.Users;
import com.learningapp.R;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>{
    private Context mContext;
    private List<Story> mStory;

    public StoryAdapter(Context mContext, List<Story> mStory) {
        this.mContext = mContext;
        this.mStory = mStory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==0){
            view = LayoutInflater.from(mContext).inflate(R.layout.add_story, parent, false);
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.story_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = mStory.get(position);
        userInfo(holder, story.getUserID(), position);
        if (holder.getAdapterPosition()!=0){
            seenStory(holder, story.getUserID());
        }

        if (holder.getAdapterPosition()==0){
            myStory(holder.add_storyText, holder.add_storyImage, false);
        }

        holder.itemView.setOnClickListener(v -> {
            if (holder.getAdapterPosition() == 0) {
                myStory(holder.add_storyText, holder.story_image_seen, true);
            } else {
                //TODO: go to story
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStory.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView story_image, story_image_seen, add_storyImage;
        TextView story_username, add_storyText;
        public ViewHolder(View itemView) {
            super(itemView);
            story_image = itemView.findViewById(R.id.story_image);
            story_image_seen = itemView.findViewById(R.id.story_image_seen);
            add_storyImage = itemView.findViewById(R.id.add_storyImage);
            story_username = itemView.findViewById(R.id.story_username);
            add_storyText = itemView.findViewById(R.id.add_storyText);
        }
    }

    private void userInfo(final ViewHolder viewHolder, String userId, final int pos){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                assert user != null;
                Glide.with(mContext).load(user.getImage_url()).into(viewHolder.add_storyImage);
                if (pos!=0){
                    Glide.with(mContext).load(user.getImage_url()).into(viewHolder.story_image);
                    viewHolder.story_username.setText(user.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void myStory(final TextView textView, final ImageView imageView, final boolean onClick){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                long timeCurrent = System.currentTimeMillis();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Story story = dataSnapshot.getValue(Story.class);
                    assert story != null;
                    if (timeCurrent>story.getStartTime() && timeCurrent<story.getEndTime()){
                        count++;
                    }
                }
                if (onClick){
                    Intent addStory = new Intent(mContext, AddStoryActivity.class);
                    mContext.startActivity(addStory);
                }else {
                    if (count>0){
                      //  textView.setText("My Story");
                        textView.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                    }else {
                        textView.setText("Your Story");
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seenStory(final ViewHolder viewHolder, String userID){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Story").child(userID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (!dataSnapshot.child("views").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists() && System.currentTimeMillis() < dataSnapshot.getValue(Story.class).getEndTime()){
                        i++;
                    }
                }
                if (i>0) {
                    viewHolder.story_image.setVisibility(View.VISIBLE);
                    viewHolder.story_image_seen.setVisibility(View.GONE);
                }else {
                    viewHolder.story_image.setVisibility(View.GONE);
                    viewHolder.story_image_seen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

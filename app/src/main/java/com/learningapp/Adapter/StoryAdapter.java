package com.learningapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learningapp.Modal.Story;
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
        if (viewType==0){
            View view = LayoutInflater.from(mContext).inflate(R.layout.add_story, parent, false);
            return new StoryAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.story_item, parent, false);
            return new StoryAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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
}

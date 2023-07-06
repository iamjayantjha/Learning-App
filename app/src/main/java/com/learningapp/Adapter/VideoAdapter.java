package com.learningapp.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learningapp.Modal.VideoAct;
import com.learningapp.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{
    private List<VideoAct> videoList;
    private Context mContext;

    public VideoAdapter(List<VideoAct> videoList, Context mContext) {
        this.videoList = videoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((mContext)).inflate(R.layout.video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoAct video = videoList.get(position);
        holder.setVideo(video);
        holder.videoView.setOnLongClickListener(v -> {
            if (holder.videoView.isPlaying()) {
                holder.videoView.pause();
                return true;
            }
            else {
                holder.videoView.start();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        VideoView videoView;
        TextView videoTitle, videoCaption;
        ProgressBar progressBar;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoCaption = itemView.findViewById(R.id.videoCaption);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public void setVideo(VideoAct video){
            videoView.setVideoPath(video.getVideoUrl());
            videoTitle.setText(video.getVideoTitle());
            videoCaption.setText(video.getVideoCaption());
            videoView.setOnPreparedListener(mp -> {
                progressBar.setVisibility(View.GONE);
                mp.start();
                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                float scale = videoRatio / screenRatio;
                if (scale >= 1f) {
                    videoView.setScaleX(scale);
                } else {
                    videoView.setScaleY(1f / scale);
                }
            });
            videoView.setOnCompletionListener(mp -> videoView.start());
        }
    }
}

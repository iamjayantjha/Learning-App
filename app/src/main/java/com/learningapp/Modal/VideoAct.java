package com.learningapp.Modal;

public class VideoAct {
    public String videoUrl,videoTitle, videoCaption, videoId;

    public VideoAct() {
    }

    public VideoAct(String videoUrl, String videoTitle, String videoCaption, String videoId) {
        this.videoUrl = videoUrl;
        this.videoTitle = videoTitle;
        this.videoCaption = videoCaption;
        this.videoId = videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoCaption() {
        return videoCaption;
    }

    public void setVideoCaption(String videoCaption) {
        this.videoCaption = videoCaption;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}

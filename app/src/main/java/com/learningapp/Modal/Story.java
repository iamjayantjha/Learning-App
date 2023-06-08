package com.learningapp.Modal;

public class Story {
    public String imageURL;
    public long startTime;
    public long endTime;
    public String storyID;
    public String userID;

    public Story() {
    }

    public Story(String imageURL, long startTime, long endTime, String storyID, String userID) {
        this.imageURL = imageURL;
        this.startTime = startTime;
        this.endTime = endTime;
        this.storyID = storyID;
        this.userID = userID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStoryID() {
        return storyID;
    }

    public void setStoryID(String storyID) {
        this.storyID = storyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

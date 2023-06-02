package com.learningapp.Modal;

public class Posts {
    String caption, location, postId, publisher, postImage,date;

    public Posts(String caption, String location, String postId, String publisher, String postImage, String date) {
        this.caption = caption;
        this.location = location;
        this.postId = postId;
        this.publisher = publisher;
        this.postImage = postImage;
        this.date = date;
    }

    public Posts() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }
}
package com.learningapp.Modal;

public class Posts {
    String caption, location, postId, publisher, postImage;

    public Posts(String caption, String location, String postId, String publisher, String postImage) {
        this.caption = caption;
        this.location = location;
        this.postId = postId;
        this.publisher = publisher;
        this.postImage = postImage;
    }

    public Posts() {
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
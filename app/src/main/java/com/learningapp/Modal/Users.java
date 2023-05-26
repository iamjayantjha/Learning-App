package com.learningapp.Modal;

public class Users {
    String bio;
    String name;
    String email;
    String username;
    String image_url;
    String id;

    public Users(String bio, String name, String email, String username, String image_url, String id) {
        this.bio = bio;
        this.name = name;
        this.email = email;
        this.username = username;
        this.image_url = image_url;
        this.id = id;
    }

    public Users() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
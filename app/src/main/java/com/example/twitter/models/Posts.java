package com.example.twitter.models;

import java.util.List;

public class Posts {
    private String post;
    private String image;
    private String userId;

    public Posts(String post, String image, String userId) {
        this.post = post;
        this.image = image;
        this.userId = userId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

package com.example.chat.dto;

import com.example.chat.entity.Posts;

import java.util.List;

public class PostResponse {

    private List<Posts> posts;
    private List<Posts> userPosts;
    private String name;

    public PostResponse(List<Posts> posts, List<Posts> userPosts, String name) {
        this.posts = posts;
        this.userPosts = userPosts;
        this.name = name;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Posts> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Posts> userPosts) {
        this.userPosts = userPosts;
    }
}

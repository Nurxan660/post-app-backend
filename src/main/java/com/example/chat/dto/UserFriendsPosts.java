package com.example.chat.dto;

import com.example.chat.entity.Posts;

import java.util.List;

public class UserFriendsPosts {
    private List<Posts> posts;
    private String name;

    public UserFriendsPosts(List<Posts> posts, String name) {
        this.posts = posts;
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
}

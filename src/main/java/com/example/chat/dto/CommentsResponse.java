package com.example.chat.dto;

import com.example.chat.entity.Comments;

import java.util.List;

public class CommentsResponse {
    private List<Comments> comments;
    private String name;

    public CommentsResponse(List<Comments> comments, String name) {
        this.comments = comments;
        this.name = name;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

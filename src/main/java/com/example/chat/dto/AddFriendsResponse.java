package com.example.chat.dto;

public class AddFriendsResponse {
    private String nickname;
    private String firstName;
    private String lastName;

    public AddFriendsResponse(String nickname, String firstName, String lastName) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

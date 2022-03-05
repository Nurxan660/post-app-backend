package com.example.chat.dto;

public class JwtResponse {
    private String token;
    private String email;
    private Long id;
    private String nickname;

    public JwtResponse(String token, String email, Long id,String nickname) {
        this.token = token;
        this.email = email;
        this.id = id;
        this.nickname=nickname;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

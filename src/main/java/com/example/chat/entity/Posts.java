package com.example.chat.entity;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name="createdAt")
    private LocalDateTime localDateTime;

    private Boolean isCommentsEnabled=true;

    @Enumerated(EnumType.STRING)
    private DestinationStatus destinationStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Boolean getCommentsEnabled() {
        return isCommentsEnabled;
    }

    public void setCommentsEnabled(Boolean commentsEnabled) {
        isCommentsEnabled = commentsEnabled;
    }

    public DestinationStatus getDestinationStatus() {
        return destinationStatus;
    }

    public void setDestinationStatus(DestinationStatus destinationStatus) {
        this.destinationStatus = destinationStatus;
    }
}

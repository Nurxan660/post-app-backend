package com.example.chat.entity;

import com.example.chat.model.UserFriendsKey;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name="user_friends")
public class UserFriends {

    @EmbeddedId
    UserFriendsKey id;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("friend_id")
    @JoinColumn(name="friend_id",nullable = false)
    private Friend sender;

    @ManyToOne
    @MapsId("recipient_id")
    @JoinColumn(name="recipient_id",nullable = false)
    private User recipient;

    @Enumerated(EnumType.STRING)
    private FriendReqStatus status= FriendReqStatus.PENDING;

    public UserFriends() {

    }

    public UserFriends(Friend sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public Friend getSender() {

        return sender;
    }

    public void setSender(Friend sender) {
        this.sender = sender;
    }



    public FriendReqStatus getStatus() {
        return status;
    }

    public void setStatus(FriendReqStatus status) {
        this.status = status;
    }

    public UserFriendsKey getId() {
        return id;
    }

    public void setId(UserFriendsKey id) {
        this.id = id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

}

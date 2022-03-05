package com.example.chat.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFriendsKey implements Serializable {

    @Column(name="friend_id")
    Long friendId;

    @Column(name="recipient_id")
    Long recipientId;

    public UserFriendsKey() {

    }

    public UserFriendsKey(Long friendId, Long recipientId) {
        this.friendId = friendId;
        this.recipientId = recipientId;
    }

    public Long getSenderId() {
        return friendId;
    }

    public void setSenderId(Long senderId) {
        this.friendId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFriendsKey that = (UserFriendsKey) o;
        return Objects.equals(friendId, that.friendId) && Objects.equals(recipientId, that.recipientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendId, recipientId);
    }
}

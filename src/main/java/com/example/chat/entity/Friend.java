package com.example.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id",unique = true)
    private User user;
    /*@JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<UserFriends> userFriends;*/


    public Friend() {
    }

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

   /* public List<UserFriends> getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(List<UserFriends> userFriends) {
        this.userFriends = userFriends;
    }*/
}

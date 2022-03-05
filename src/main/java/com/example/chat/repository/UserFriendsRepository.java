package com.example.chat.repository;

import com.example.chat.entity.Friend;
import com.example.chat.entity.FriendReqStatus;
import com.example.chat.entity.User;
import com.example.chat.entity.UserFriends;
import com.example.chat.model.UserFriendsKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserFriendsRepository extends CrudRepository<UserFriends, UserFriendsKey> {
    Optional<UserFriends> findById(UserFriendsKey id);
    List<UserFriends> findByRecipientIdAndStatus(Long id, FriendReqStatus status);
    List<UserFriends> findBySenderAndStatus(Friend friend, FriendReqStatus status);

}

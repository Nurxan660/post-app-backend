package com.example.chat.repository;

import com.example.chat.entity.Friend;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FriendRepository extends CrudRepository<Friend,Long> {
    Optional<Friend> findByUserId(Long id);
}

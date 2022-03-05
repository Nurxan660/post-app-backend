package com.example.chat.repository;

import com.example.chat.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByNicknameStartingWithAndIdNot(String nickname,Long id);
}

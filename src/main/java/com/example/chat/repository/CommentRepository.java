package com.example.chat.repository;

import com.example.chat.entity.Comments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comments,Long> {
    List<Comments> findByPostsId(Long id);
}

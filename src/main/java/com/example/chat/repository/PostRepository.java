package com.example.chat.repository;

import com.example.chat.entity.DestinationStatus;
import com.example.chat.entity.Posts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Posts,Long> {
    List<Posts> findByUserIdOrderById(Long id);
    List<Posts> findByUserIdAndDestinationStatus(Long id,DestinationStatus destinationStatus);
    List<Posts> findByDestinationStatus(DestinationStatus destinationStatus);
}

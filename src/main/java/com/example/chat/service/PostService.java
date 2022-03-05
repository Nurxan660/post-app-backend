package com.example.chat.service;

import com.example.chat.dto.CreatePostRequest;
import com.example.chat.dto.PostResponse;
import com.example.chat.entity.DestinationStatus;
import com.example.chat.entity.Posts;
import com.example.chat.entity.User;
import com.example.chat.repository.PostRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    public PostResponse createNewPost(CreatePostRequest createPostRequest){
        User user=userRepository.findById(createPostRequest.getUserId()).orElseThrow(()->new RuntimeException("not found"));
        Posts post =new Posts();
        post.setUser(user);
        post.setContent(createPostRequest.getContent());
        post.setLocalDateTime(LocalDateTime.now());
        post.setDestinationStatus(DestinationStatus.TO_ALL_AUTH_USERS);
        postRepository.save(post);
        PostResponse postResponse=new PostResponse(getPostsForAllAuth(),getPosts(createPostRequest.getUserId()),"posts");
        return postResponse;
    }
    public PostResponse createNewPostForFriends(CreatePostRequest createPostRequest){
        User user=userRepository.findById(createPostRequest.getUserId()).orElseThrow(()->new RuntimeException("not found"));
        Posts post =new Posts();
        post.setUser(user);
        post.setContent(createPostRequest.getContent());
        post.setLocalDateTime(LocalDateTime.now());
        post.setDestinationStatus(DestinationStatus.TO_FRIEND);
        postRepository.save(post);
        PostResponse postToFriendResponse=new PostResponse(getPostsForFriends(createPostRequest.getUserId()),getPosts(createPostRequest.getUserId()),"postForFriends");
        return postToFriendResponse;
    }
    public PostResponse createNewPostForAll(CreatePostRequest createPostRequest){
        User user=userRepository.findById(createPostRequest.getUserId()).orElseThrow(()->new RuntimeException("not found"));
        Posts post =new Posts();
        post.setUser(user);
        post.setContent(createPostRequest.getContent());
        post.setLocalDateTime(LocalDateTime.now());
        post.setDestinationStatus(DestinationStatus.TO_ALL_USERS);
        postRepository.save(post);
        PostResponse postToFriendResponse= new PostResponse(getPostsForAll(),getPosts(createPostRequest.getUserId()), "postForAll");
        return postToFriendResponse;
    }


    public List<Posts> getPosts(Long id){

        return postRepository.findByUserIdOrderById(id);
    }
    public List<Posts> getPostsForFriends(Long id){

        return postRepository.findByUserIdAndDestinationStatus(id,DestinationStatus.TO_FRIEND);
    }

    public List<Posts> getPostsForAllAuth(){

        return postRepository.findByDestinationStatus(DestinationStatus.TO_ALL_AUTH_USERS);
    }
    public List<Posts> getPostsForAll(){

        return postRepository.findByDestinationStatus(DestinationStatus.TO_ALL_USERS);
    }
   /* public PostResponse getAllPosts(){
        List<Posts> posts= (List<Posts>) postRepository.findAll();
        PostResponse postResponse=new PostResponse(posts,"posts");
        return postResponse;
    }*/








}

package com.example.chat.controller;

import com.example.chat.dto.CreatePostRequest;
import com.example.chat.dto.PostResponse;
import com.example.chat.dto.UserFriendsPosts;
import com.example.chat.service.AddToFriendService;
import com.example.chat.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);


    @Autowired
    private PostService postService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AddToFriendService addToFriendService;

    @MessageMapping("/post")
    public void getAllPosts(@Payload CreatePostRequest createPostRequest) {
        logger.info("message from client to /post",createPostRequest);
        PostResponse postResponse =postService.createNewPost(createPostRequest);
        logger.info("response for subscribers",createPostRequest);
        messagingTemplate.convertAndSend( "/user/posts",postResponse );

    }
    @MessageMapping("/postForFriends")
    public void postForFriends(@Payload CreatePostRequest createPostRequest) {
        logger.info("message from client to /postForFriends",createPostRequest);
        PostResponse postResponse =postService.createNewPostForFriends(createPostRequest);
        logger.info("response for subscribers",createPostRequest);
        addToFriendService.getFriends(createPostRequest.getUserId()).forEach((d)->{
            messagingTemplate.convertAndSend( "/user/"+"postTo/"+d.getRecipient().getId(),postResponse);
        });

        messagingTemplate.convertAndSend( "/user/"+"postsFriend/"+createPostRequest.getUserId(),new UserFriendsPosts(postResponse.getUserPosts(),"friendsPosts"));
    }
    @MessageMapping("/postForAll")
    public void postForAll(@Payload CreatePostRequest createPostRequest) {
        logger.info("message from client to /postForFriends",createPostRequest);
        PostResponse postResponse =postService.createNewPostForAll(createPostRequest);
        logger.info("response for subscribers",createPostRequest);
            messagingTemplate.convertAndSend( "/user/"+"postForAll",postResponse);

    }


    @GetMapping("/get/posts")
    public ResponseEntity getPosts(@RequestParam Long id){
        return ResponseEntity.ok(postService.getPosts(id));

    }
    @GetMapping("/get/postsForAll")
    public ResponseEntity getPostsForAll(){
        return ResponseEntity.ok(postService.getPostsForAllAuth());
    }
    @GetMapping("/get/postsForAllNotAuth")
    public ResponseEntity getPostsForAllNotAuth(){
        return ResponseEntity.ok(postService.getPostsForAll());
    }

   /* @GetMapping("/get/posts/all")
    public ResponseEntity getPostsAll(){
        return ResponseEntity.ok(postService.getAllPosts());

    }*/
}

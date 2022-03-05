package com.example.chat.controller;

import com.example.chat.dto.AddCommentRequest;
import com.example.chat.dto.ChangeCommentStatusReq;
import com.example.chat.dto.CommentsResponse;
import com.example.chat.dto.CreatePostRequest;
import com.example.chat.entity.Comments;
import com.example.chat.entity.Posts;
import com.example.chat.service.CommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);


    @Autowired
    private CommentsService commentsService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/comments")
    public void saveComments(@Payload AddCommentRequest addCommentRequest) {
   logger.info("send message from client to /comments ",addCommentRequest);
        CommentsResponse commentsResponse =commentsService.addComment(addCommentRequest);
        logger.info("send to subcribers",commentsResponse);
        messagingTemplate.convertAndSend( "/user/comments",commentsResponse);

    }

    @GetMapping("/get/comments")
    public ResponseEntity getComments(@RequestParam Long id){
        return ResponseEntity.ok(commentsService.getAllCommentsForPost(id));
    }

    @GetMapping("comments/changeStatus")
    public ResponseEntity changeCommentStatus(@RequestParam Long id){
        commentsService.setDisabled(id);
        return ResponseEntity.ok("status changed");
    }

}

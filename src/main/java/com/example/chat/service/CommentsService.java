package com.example.chat.service;

import com.example.chat.dto.AddCommentRequest;
import com.example.chat.dto.ChangeCommentStatusReq;
import com.example.chat.dto.CommentsResponse;
import com.example.chat.entity.Comments;
import com.example.chat.entity.Posts;
import com.example.chat.entity.User;
import com.example.chat.repository.CommentRepository;
import com.example.chat.repository.PostRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentsService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    public CommentsResponse addComment(AddCommentRequest addCommentRequest){
        Posts posts=postRepository.findById(addCommentRequest.getPostId()).orElseThrow(()->new RuntimeException("not found"));
        User sender=userRepository.findById(addCommentRequest.getSenderId()).orElseThrow(()->new RuntimeException("not found"));
        Comments comments=new Comments();
        comments.setPosts(posts);
        comments.setUser(sender);
        comments.setContent(addCommentRequest.getContent());
        comments.setLocalDateTime(LocalDateTime.now());
        commentRepository.save(comments);
        CommentsResponse commentsResponse=new CommentsResponse(getAllCommentsForPost(addCommentRequest.getPostId()),"comments");
        return commentsResponse;
    }

    public List<Comments> getAllCommentsForPost(Long postId){
       return  commentRepository.findByPostsId(postId);
    }

    public void setDisabled(Long id){
        Posts posts=postRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        posts.setCommentsEnabled(!posts.getCommentsEnabled());
        postRepository.save(posts);

    }
}

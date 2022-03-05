package com.example.chat.controller;


import com.example.chat.dto.AddFriendsRequest;
import com.example.chat.dto.AddFriendsResponse;
import com.example.chat.dto.ChangeStatusRequest;
import com.example.chat.entity.FriendReqStatus;
import com.example.chat.entity.UserFriends;
import com.example.chat.service.AddToFriendService;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AddToFriendController {
    private static final Logger logger = LoggerFactory.getLogger(AddToFriendController.class);

    @Autowired
    private AddToFriendService addToFriendService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/friend/add")
    public void friendRequest(@Payload AddFriendsRequest addFriendsRequest){
        logger.info("addFriendsRequest for add friends",addFriendsRequest);

            List<UserFriends> userFriends=addToFriendService.saveFriend(addFriendsRequest);
            logger.info("return list of user friends and send it to destination",userFriends);
            messagingTemplate.convertAndSendToUser(String.valueOf(addFriendsRequest.getRecipientId()),"/queue/friend/add",userFriends);

    }
    @MessageMapping("/friend/req")
    public void changeStatus(@Payload ChangeStatusRequest c) {
        logger.info("ChangeStatusRequest for change status",c);

        String message = addToFriendService.changeStatus(c.getSenderId(), c.getRecipientId(), c.getStatus());
        messagingTemplate.convertAndSendToUser(String.valueOf(c.getSenderId()), "/queue/friend/req", message);

        logger.info("return message which containt status",message);


    }

    @GetMapping("/find")
    public ResponseEntity getFriends(@RequestParam String nickname,@RequestParam Long currentUserId){
        try{
            return ResponseEntity.ok(addToFriendService.findAllUsersByNickname(nickname,currentUserId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/friends/withStatus")
    public ResponseEntity getFriendsWithStatus(@RequestParam Long recipientId){
        try{
            return ResponseEntity.ok(addToFriendService.getFriendReqStatus(recipientId, FriendReqStatus.PENDING));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/friends/get")
    public ResponseEntity getFriends(@RequestParam Long userId) {

        try{
            return ResponseEntity.ok(addToFriendService.getFriends(userId));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

















    }

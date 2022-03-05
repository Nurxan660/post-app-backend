package com.example.chat.service;

import com.example.chat.dto.AddFriendsRequest;
import com.example.chat.entity.Friend;
import com.example.chat.entity.FriendReqStatus;
import com.example.chat.entity.User;
import com.example.chat.entity.UserFriends;
import com.example.chat.model.UserFriendsKey;
import com.example.chat.repository.FriendRepository;
import com.example.chat.repository.UserFriendsRepository;
import com.example.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddToFriendService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserFriendsRepository userFriendsRepository;


    public List<User> findAllUsersByNickname(String nickname,Long id){
        List<User> friends= userRepository.findAllByNicknameStartingWithAndIdNot(nickname,id);
        if(friends.size()==0){
            throw new RuntimeException("no result");
        }
        else{
            return friends;
        }
    }


    public List<UserFriends> saveFriend(AddFriendsRequest addFriendsRequest){
        Friend existFriend=friendRepository.findByUserId(addFriendsRequest.getSenderId()).orElse(null);
        User sender =userRepository.findById(addFriendsRequest.getSenderId()).orElseThrow(()->new RuntimeException("not found"));
        User recipient =userRepository.findById(addFriendsRequest.getRecipientId()).orElseThrow(()->new RuntimeException("not found"));
        UserFriendsKey userFriendsKey=new UserFriendsKey();
        UserFriends userFriends=new UserFriends();


        if(existFriend==null){
            Friend friend=new Friend();
            friend.setUser(sender);
            friendRepository.save(friend);
            userFriends.setSender(friend);
            userFriendsKey.setSenderId(friend.getId());


        }
        else{
            userFriends.setSender(existFriend);
            userFriendsKey.setSenderId(existFriend.getId());
        }
        userFriendsKey.setRecipientId(recipient.getId());
        userFriends.setRecipient(recipient);
        userFriends.setId(userFriendsKey);
        userFriendsRepository.save(userFriends);
        return getFriendReqStatus(addFriendsRequest.getRecipientId(),FriendReqStatus.PENDING);
    }

    public List<UserFriends> getFriendReqStatus(Long recipientId, FriendReqStatus status){
        return userFriendsRepository.findByRecipientIdAndStatus(recipientId,status);
    }

    public String changeStatus(Long senderId,Long recipientId,String status){
        String message=new String();
        Friend existFriend=friendRepository.findByUserId(recipientId).orElse(null);
        Friend sender=friendRepository.findById(senderId).orElseThrow(()->new RuntimeException("not found"));
        User recipient=userRepository.findById(recipientId).orElseThrow(()->new RuntimeException("not found"));
        UserFriendsKey userFriendsKey=new UserFriendsKey(senderId,recipientId);
        UserFriends userFriends1=new UserFriends();
        UserFriends userFriends=userFriendsRepository.findById(userFriendsKey)
                .orElseThrow(()->new RuntimeException("not found"));
        if(status.equals("Accept")){
            userFriends.setStatus(FriendReqStatus.ACCEPTED);
            if(existFriend==null) {
                existFriend=new Friend();
                existFriend.setUser(recipient);
                friendRepository.save(existFriend);
            }
            Friend rec=friendRepository.findByUserId(recipientId).orElseThrow(()->new RuntimeException("not found"));
            UserFriendsKey userFriendsKey1=new UserFriendsKey(rec.getId(),sender.getUser().getId());
            userFriends1.setId(userFriendsKey1);
            userFriends1.setRecipient(sender.getUser());
            userFriends1.setSender(existFriend);
            userFriends1.setStatus(FriendReqStatus.ACCEPTED);
            message=" принял вашу заявку";
        }
        else{
            userFriends.setStatus(FriendReqStatus.REJECTED);
            message=" отклонил вашу заявку";
        }
        userFriendsRepository.save(userFriends);
        userFriendsRepository.save(userFriends1);

        return userFriends.getRecipient().getNickname()+message;
    }

    public List<UserFriends> getFriends(Long userId){
        Friend existFriend=friendRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("not found"));

        return userFriendsRepository.findBySenderAndStatus(existFriend,FriendReqStatus.ACCEPTED);
    }


















}

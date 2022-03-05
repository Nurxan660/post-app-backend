package com.example.chat.service;


import com.example.chat.controller.AddToFriendController;
import com.example.chat.dto.JwtResponse;
import com.example.chat.dto.LoginRequest;
import com.example.chat.dto.RegistrationRequest;
import com.example.chat.entity.User;
import com.example.chat.repository.UserRepository;
import com.example.chat.security.UserDetailsImpl;
import com.example.chat.security.jwt.JwtUtils;
import com.example.chat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register (RegistrationRequest registrationRequest){
        User user=new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setLocalDateTime(LocalDateTime.now());
        user.setLastName(registrationRequest.getLastName());
        user.setNickname(registrationRequest.getNickname());
        return userRepository.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        logger.info("just validate email and password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtUtils.generateJwtToken(authentication);
        logger.info("created token",token);
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        return new JwtResponse(token,loginRequest.getEmail(),userDetails.getId(),userDetails.getNickname());

    }


}

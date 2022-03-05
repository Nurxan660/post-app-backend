package com.example.chat.controller;


import com.example.chat.dto.JwtResponse;
import com.example.chat.dto.LoginRequest;
import com.example.chat.dto.RegistrationRequest;
import com.example.chat.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/reg")
    public ResponseEntity register(@RequestBody RegistrationRequest registrationRequest){
        logger.info("registration request with dto ",registrationRequest);

        try{
            authService.register(registrationRequest);
            return ResponseEntity.ok("User registered succesfully");
        }
        catch (DataIntegrityViolationException e){
            logger.error("Email already exist");
            return ResponseEntity.badRequest().body("Email already exist");
        }

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        try{

            JwtResponse jwtResponse=authService.login(loginRequest);
            logger.info("jwtResponse",jwtResponse);
            return ResponseEntity.ok(jwtResponse);

        }
        catch (Exception e){
            logger.error("incorrect email or password");
            return ResponseEntity.badRequest().body("incorrect email or password");
        }

    }







}

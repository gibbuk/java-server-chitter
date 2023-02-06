package com.chitter.server.controller;


import com.chitter.server.model.User;
import com.chitter.server.payload.request.NewUserRequest;
import com.chitter.server.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chitter.server.repository.UserRepository;


@RestController
@RequestMapping
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody NewUserRequest newUserRequest) {

        try {

            User user = newUserRequest.getNewUser();

            if (userRepository.existsByUsername(user.getUsername())) {
                return new ResponseEntity<>(new MessageResponse("Username already taken"), HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>(new MessageResponse("Email already taken"), HttpStatus.BAD_REQUEST);
            }

            userRepository.save(new User(user.getUsername(), user.getName(), user.getEmail(), user.getPassword()));

            MessageResponse message = new MessageResponse("Sign up successful");

            return new ResponseEntity<>(message, HttpStatus.CREATED);

        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

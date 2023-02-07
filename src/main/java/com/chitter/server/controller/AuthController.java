package com.chitter.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chitter.server.repository.UserRepository;
import com.chitter.server.model.User;
import com.chitter.server.payload.request.LoginRequest;
import com.chitter.server.payload.request.NewUserRequest;
import com.chitter.server.payload.response.MessageResponse;
import com.chitter.server.payload.response.LoginResponse;

import java.util.Optional;


@CrossOrigin(origins = "*")
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

            return new ResponseEntity<>(new MessageResponse("Sign up successful"), HttpStatus.CREATED);

        } catch(Exception e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {

        try {
            Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

            if (user.isPresent() && loginRequest.getPassword().equals(user.get().getPassword())) {
                return new ResponseEntity<>(new LoginResponse("Login success", user.get()), HttpStatus.OK);
            }

            return new ResponseEntity<>(new LoginResponse("Details not found", null), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(new LoginResponse("Something went wrong", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

package com.chitter.server.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chitter.server.repository.UserRepository;
import com.chitter.server.model.User;
import com.chitter.server.payload.request.LoginRequest;
import com.chitter.server.payload.request.NewUserRequest;
import com.chitter.server.payload.response.LoginResponse;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid NewUserRequest request) {

        try {

            if (userRepository.existsByUsername(request.getUsername())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            User newUser = new User(request.getUsername(),
                    request.getName(),
                    request.getEmail(),
                    request.getPassword());


            return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.CREATED);

        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest request) {

        try {
            Optional<User> user = userRepository.findByUsername(request.getUsername());

            if (user.isPresent() && request.getPassword().equals(user.get().getPassword())) {
                return new ResponseEntity<>(new LoginResponse(user.get()), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

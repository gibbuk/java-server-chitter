package com.chitter.server;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chitter.server.payload.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.chitter.server.controller.AuthController;
import com.chitter.server.payload.request.NewUserRequest;
import com.chitter.server.model.User;
import com.chitter.server.repository.UserRepository;

import java.util.Optional;


@WebMvcTest(AuthController.class)
public class AuthControllerTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;




    // Register route tests
    @Test
    void shouldReturnASuccessMessagePayloadOnReceivingValidRequest() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");
        NewUserRequest newUserRequest = new NewUserRequest(user);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Sign up successful"))
                .andDo(print());
    }

    @Test
    void shouldReturnInternalServerErrorInEventOfException() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");
        NewUserRequest newUserRequest = new NewUserRequest(user);
        RuntimeException e = new RuntimeException("Server Message");

        when(userRepository.save(Mockito.any(User.class))).thenThrow(e);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUserRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(e.getMessage()))
                .andDo(print());
    }

    @Test
    void shouldReturnAnErrorMessageIfUsernameTaken() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");
        NewUserRequest newUserRequest = new NewUserRequest(user);

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already taken"))
                .andDo(print());
    }

    @Test
    void shouldReturnAnErrorMessageIfEmailAlreadyTaken() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");
        NewUserRequest newUserRequest = new NewUserRequest(user);

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(newUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already taken"))
                .andDo(print());
    }

    // login route tests

    @Test
    void shouldReturnSuccessMessageAndUserOnReceivingValidRequest() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");
        LoginRequest loginRequest = new LoginRequest(user.getUsername(), user.getPassword());

        String userAsJson = objectMapper.writeValueAsString(user);

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login success"))
                .andExpect(jsonPath("$.user.username").value(user.getUsername()))
                .andExpect(jsonPath("$.user.name").value(user.getName()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.user.password").value(user.getPassword()))
                .andDo(print());
    }

    @Test
    void shouldReturnErrorMessageReceivingInvalidPassword() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");
        LoginRequest loginRequest = new LoginRequest(user.getUsername(), "wrong password");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Details not found"))
                .andExpect(jsonPath("$.user").isEmpty())
                .andDo(print());
    }

    @Test
    void shouldReturnErrorMessageReceivingNonExistentUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("nonexistentUser", "wrong password");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Details not found"))
                .andExpect(jsonPath("$.user").isEmpty())
                .andDo(print());
    }

    @Test
    void shouldReturnInternalServerErrorIfExceptionThrown() throws Exception {
        LoginRequest loginRequest = new LoginRequest("nonexistentUser", "wrong password");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenThrow(new RuntimeException());
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Something went wrong"))
                .andExpect(jsonPath("$.user").isEmpty())
                .andDo(print());
    }
}

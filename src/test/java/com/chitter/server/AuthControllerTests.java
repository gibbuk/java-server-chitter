package com.chitter.server;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.chitter.server.controller.AuthController;
import com.chitter.server.model.User;
import com.chitter.server.repository.UserRepository;


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

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Sign up successful"))
                .andDo(print());
    }

    @Test
    void shouldReturnInternalServerErrorInEventOfException() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");
        RuntimeException e = new RuntimeException("Server Message");

        when(userRepository.save(Mockito.any(User.class))).thenThrow(e);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(e.getMessage()))
                .andDo(print());
    }

    @Test
    void shouldReturnAnErrorMessageIfUsernameTaken() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username already taken"))
                .andDo(print());
    }

    @Test
    void shouldReturnAnErrorMessageIfEmailAlreadyTaken() throws Exception {
        User user = new User("username", "name", "email@email.com", "password");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already taken"))
                .andDo(print());
    }

    // signin route tests




}

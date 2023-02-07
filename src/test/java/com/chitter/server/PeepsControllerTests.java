package com.chitter.server;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.chitter.server.model.User;
import com.chitter.server.payload.request.PeepRequest;
import com.chitter.server.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.chitter.server.controller.PeepsController;
import com.chitter.server.model.Peep;
import com.chitter.server.repository.PeepsRepository;

@WebMvcTest(PeepsController.class)
public class PeepsControllerTests {

    @MockBean
    private PeepsRepository peepsRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnListOfAllPeeps() throws Exception {
        List<Peep> peeps = new ArrayList<>(
                Arrays.asList(new Peep("testusername1", "realname1", "testcontent1", "2023-02-03T11:18:31.077Z"),
                        new Peep("testusername2", "realname2", "testcontent2", "2022-02-03T11:18:31.077Z"),
                        new Peep("testusername3", "realname3", "testcontent3", "2021-02-03T11:18:31.077Z")));

        when(peepsRepository.findAll()).thenReturn(peeps);
        mockMvc.perform(get("/peeps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(peeps.size()))
                .andDo(print());
    }

    @Test
    void shouldReturnNoContentStatus() throws Exception {
        List<Peep> peeps = new ArrayList<>();

        when(peepsRepository.findAll()).thenReturn(peeps);
        mockMvc.perform(get("/peeps"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldReturnTheAddedPeepAndMessage() throws Exception {
        User user = new User("username", "real name", "email@email.com", "password");
        Peep peep = new Peep("username", "real name", "peep content", "2023-02-03T11:18:31.077Z");
        PeepRequest peepRequest = new PeepRequest(user, peep);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(peepsRepository.save(Mockito.any(Peep.class))).thenReturn(peep);
        mockMvc.perform(post("/peeps").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peepRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Peep posted!"))
                .andExpect(jsonPath("$.peep.username").value(peep.getUsername()))
                .andExpect(jsonPath("$.peep.realName").value(peep.getRealName()))
                .andExpect(jsonPath("$.peep.content").value(peep.getContent()))
                .andExpect(jsonPath("$.peep.dateCreated").value(peep.getDateCreated()))
                .andDo(print());

    }

    @Test
    void shouldReturnAnErrorIfNoPeepSupplied() throws Exception {
        User user = new User("username", "real name", "email@email.com", "password");
        PeepRequest peepRequest = new PeepRequest(user, null);

        mockMvc.perform(post("/peeps").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peepRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: no content supplied"))
                .andExpect(jsonPath("$.peep").isEmpty())
                .andDo(print());
    }

    @Test
    void shouldReturnAnErrorIfNoUserCredentialsSupplied() throws Exception {
        Peep peep = new Peep("username", "real name", "peep content", "2023-02-03T11:18:31.077Z");
        PeepRequest peepRequest = new PeepRequest(null, peep);

        mockMvc.perform(post("/peeps").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peepRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: no valid user"))
                .andDo(print());


    }

    @Test
    void shouldReturnAnErrorIfUserPasswordNotValid() throws Exception {
        User user = new User("username", "real name", "email@email.com", "password");
        Peep peep = new Peep("username", "real name", "peep content", "2023-02-03T11:18:31.077Z");
        PeepRequest peepRequest = new PeepRequest(user, peep);

        Optional<User> userToReturn = Optional.of(new User("username", "real name", "email@email.com", "differentpassword"));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(userToReturn);
        when(peepsRepository.save(Mockito.any(Peep.class))).thenReturn(peep);
        mockMvc.perform(post("/peeps").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peepRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: no valid user"))
                .andDo(print());
    }

    @Test
    void shouldReturnAnErrorIfUserNotFoundInDatabase() throws Exception {
        User user = new User("username", "real name", "email@email.com", "password");
        Peep peep = new Peep("username", "real name", "peep content", "2023-02-03T11:18:31.077Z");
        PeepRequest peepRequest = new PeepRequest(user, peep);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(peepsRepository.save(Mockito.any(Peep.class))).thenReturn(peep);
        mockMvc.perform(post("/peeps").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peepRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: no valid user"))
                .andDo(print());
    }

    @Test
    void shouldReturnAnInternalServerErrorCode() throws Exception {
        User user = new User("username", "real name", "email@email.com", "password");
        Peep peep = new Peep("username", "real name", "peep content", "2023-02-03T11:18:31.077Z");
        PeepRequest peepRequest = new PeepRequest(user, peep);
        RuntimeException exception = new RuntimeException("Exception message");

        when(userRepository.findByUsername(user.getUsername())).thenThrow(exception);
        mockMvc.perform(post("/peeps").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peepRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(exception.getMessage()))
                .andExpect(jsonPath("$.peep").isEmpty())
                .andDo(print());
    }
}

package com.chitter.server;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chitter.server.controller.PeepsController;
import com.chitter.server.model.Peep;
import com.chitter.server.repository.PeepsRepository;

@WebMvcTest(PeepsController.class)
public class PeepsControllerTests {

    @MockBean
    private PeepsRepository peepsRepository;

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
    void shouldReturnTheAddedPeep() throws Exception {
        Peep peep = new Peep("username", "real name", "peep content", "2023-02-03T11:18:31.077Z");

        when(peepsRepository.save(peep)).thenReturn(peep);
        mockMvc.perform(post("/peeps").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(peep)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(peep.getUsername()))
                .andExpect(jsonPath("$.realName").value(peep.getRealName()))
                .andExpect(jsonPath("$.content").value(peep.getContent()))
                .andExpect(jsonPath("$.dateCreated").value(peep.getDateCreated()))
                .andDo(print());

    }

}

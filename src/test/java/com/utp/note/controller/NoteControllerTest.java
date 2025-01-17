package com.utp.note.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utp.note.model.ResponseClient;
import com.utp.note.model.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;
import com.utp.note.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private NoteController noteController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }

    @Test
    void createNote_shouldReturnCreatedStatus() throws Exception {
        NoteRequest request = new NoteRequest("Test Title", "Test Content");
        String username = "testUser";

        ResponseClient<Void> response = ResponseClient.setOk();

        when(authentication.getName())
                .thenReturn(username);
        when(noteService.createNote(request, username))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .principal(authentication))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(noteService, times(1)).createNote(request, username);
    }

    @Test
    void getAllNotes_shouldReturnOkStatusAndNotesList() throws Exception {
        String username = "testUser";
        NoteResponse noteResponse = new NoteResponse("Test Title", "Test Content");
        ResponseClientList<NoteResponse> responseClientList = ResponseClientList.setOk(List.of(noteResponse));

        when(authentication.getName()).thenReturn(username);
        when(noteService.getNotesByUser(username)).thenReturn(responseClientList);

        mockMvc.perform(get("/api/v1/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseClientList)));

        verify(noteService, times(1)).getNotesByUser(username);
    }
}
package com.utp.note.service.impl;

import com.utp.note.constant.Constant;
import com.utp.note.domain.Note;
import com.utp.note.domain.User;
import com.utp.note.helper.ResponseClient;
import com.utp.note.helper.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;
import com.utp.note.repository.NoteRepository;
import com.utp.note.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private NoteServiceImpl noteService;

    private User user;
    private NoteRequest noteRequest;
    private Note note;
    private NoteResponse noteResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail("test@example.com");

        noteRequest = new NoteRequest("Test Note", "Test Content");

        note = Note.builder()
                .note("Test Note")
                .content("Test Content")
                .user(user)
                .build();

        noteResponse = new NoteResponse("Test Note", "Test Content");

        when(userService.getByEmail(anyString())).thenReturn(user);
    }

    @Test
    void testCreateNote_Success() {
        when(noteRepository.save(any(Note.class)))
                .thenReturn(note);
        ResponseClient<Void> response = noteService.createNote(noteRequest, "test@example.com");
        assertEquals(Constant.CODE_OK, response.getCode());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testCreateNote_Exception() {
        doThrow(new RuntimeException("Database error"))
                .when(noteRepository)
                .save(any(Note.class));
        ResponseClient<Void> response = noteService.createNote(noteRequest, "test@example.com");
        assertEquals(Constant.CODE_ERROR, response.getCode());
        assertEquals(Constant.MSG_SAVE_NOTE, response.getMessage());
    }

    @Test
    void testGetNotesByUser_Success() {
        List<Note> notes = List.of(note);

        when(noteRepository.findByUser(user))
                .thenReturn(notes);

        when(modelMapper.map(any(Note.class), eq(NoteResponse.class)))
                .thenReturn(noteResponse);

        ResponseClientList<NoteResponse> response = noteService.getNotesByUser("test@example.com");
        assertEquals(1, response.getData().size());
        assertEquals("Test Note", response.getData().get(0).getNote());
        verify(noteRepository, times(1)).findByUser(user);
    }

    @Test
    void testGetNotesByUser_EmptyNotes() {
        when(noteRepository.findByUser(user))
                .thenReturn(Collections.emptyList());
        ResponseClientList<NoteResponse> response = noteService.getNotesByUser("test@example.com");
        assertEquals(Constant.CODE_EMPTY, response.getCode());
        assertEquals(Constant.MSG_GET_NOTE_EMPTY, response.getMessage());
    }

    @Test
    void testGetNotesByUser_Exception() {
        when(noteRepository.findByUser(user))
                .thenThrow(new RuntimeException("Database error"));
        ResponseClientList<NoteResponse> response = noteService.getNotesByUser("test@example.com");
        assertEquals(Constant.MSG_GET_NOTE, response.getMessage());
        assertEquals(Constant.CODE_ERROR, response.getCode());
    }
}

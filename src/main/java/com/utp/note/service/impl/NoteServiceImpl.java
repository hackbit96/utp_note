package com.utp.note.service.impl;

import com.utp.note.domain.Note;
import com.utp.note.domain.User;
import com.utp.note.model.ResponseClient;
import com.utp.note.model.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;
import com.utp.note.repository.NoteRepository;
import com.utp.note.service.NoteService;
import com.utp.note.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Override
    public ResponseClient<Void> createNote(NoteRequest request, String email) {
        User user = userService.getByEmail(email);
        Note note = Note.builder()
                .note(request.getNote())
                .content(request.getContent())
                .user(user)
                .build();
        try {
            noteRepository.save(note);
            return ResponseClient.setOk();
        } catch (Exception e) {
            log.error("Ocurrio un error al realizar registro de nota");
            return ResponseClient.setError("Ocurrio un error");
        }
    }

    @Override
    public ResponseClientList<NoteResponse> getNotesByUser(String email) {
        User user = userService.getByEmail(email);
        List<Note> notes = noteRepository.findByUser(user);
        List<NoteResponse> noteResponse = notes.stream()
                .map(note -> mapper.map(note, NoteResponse.class))
                .toList();
        return ResponseClientList.setOk(noteResponse);
    }
}

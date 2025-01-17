package com.utp.note.service.impl;

import com.utp.note.constant.Constant;
import com.utp.note.domain.Note;
import com.utp.note.domain.User;
import com.utp.note.helper.ResponseClient;
import com.utp.note.helper.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;
import com.utp.note.repository.NoteRepository;
import com.utp.note.service.NoteService;
import com.utp.note.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .createdAt(LocalDateTime.now())
                .build();
        try {
            noteRepository.save(note);
        } catch (Exception e) {
            log.error(Constant.MSG_SAVE_NOTE + ": DETALLE: {}", e.getMessage());
            return ResponseClient.setError(Constant.MSG_SAVE_NOTE);
        }
        return ResponseClient.setOk();
    }

    @Override
    public ResponseClientList<NoteResponse> getNotesByUser(String email) {
        User user = userService.getByEmail(email);
        List<Note> notes;
        try {
            notes = noteRepository.findByUser(user);
        } catch (Exception e) {
            log.error(Constant.MSG_GET_NOTE + " DETALLE: {}", e.getMessage());
            return ResponseClientList.setError(Constant.MSG_GET_NOTE);
        }

        if (ObjectUtils.isEmpty(notes)){
            log.info(Constant.MSG_GET_NOTE_EMPTY);
            return ResponseClientList.setEmpty(Constant.MSG_GET_NOTE_EMPTY);
        }

        List<NoteResponse> noteResponse = notes.stream()
                .map(note -> mapper.map(note, NoteResponse.class))
                .toList();
        return ResponseClientList.setOk(noteResponse);
    }
}

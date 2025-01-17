package com.utp.note.service;

import com.utp.note.model.ResponseClient;
import com.utp.note.model.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;

public interface NoteService {

    ResponseClient<Void> createNote(NoteRequest request, String email);

    ResponseClientList<NoteResponse> getNotesByUser(String email);

}

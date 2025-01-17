package com.utp.note.service;

import com.utp.note.helper.ResponseClient;
import com.utp.note.helper.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;

public interface NoteService {

    ResponseClient<Void> createNote(NoteRequest request, String email);

    ResponseClientList<NoteResponse> getNotesByUser(String email);

}

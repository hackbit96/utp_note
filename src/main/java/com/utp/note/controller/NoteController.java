package com.utp.note.controller;


import com.utp.note.model.BaseResponse;
import com.utp.note.model.ResponseClient;
import com.utp.note.model.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;
import com.utp.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @Operation(summary = "Record Notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
    })
    public ResponseEntity<ResponseClient<Void>> create(@RequestBody @Valid NoteRequest request, Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteService.createNote(request, authentication.getName()));
    }

    @GetMapping
    @Operation(summary = "Get Notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
    })
    public ResponseEntity<ResponseClientList<NoteResponse>> getAll(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteService.getNotesByUser(authentication.getName()));
    }


}

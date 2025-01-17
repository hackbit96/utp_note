package com.utp.note.controller;


import com.utp.note.helper.BaseResponse;
import com.utp.note.helper.ResponseClient;
import com.utp.note.helper.ResponseClientList;
import com.utp.note.model.request.NoteRequest;
import com.utp.note.model.response.NoteResponse;
import com.utp.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/note")
@Tag(name = "Note", description = "Note Services")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @Operation(summary = "Record Notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
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
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
    })
    public ResponseEntity<ResponseClientList<NoteResponse>> getAll(Authentication authentication) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteService.getNotesByUser(authentication.getName()));
    }


}

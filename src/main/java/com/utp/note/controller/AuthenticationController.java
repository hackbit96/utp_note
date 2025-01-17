package com.utp.note.controller;

import com.utp.note.model.BaseResponse;
import com.utp.note.model.ResponseClient;
import com.utp.note.model.request.SignInRequest;
import com.utp.note.model.request.SignUpRequest;
import com.utp.note.model.response.JwtAuthenticationResponse;
import com.utp.note.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication Services")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @Operation(summary = "Signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))})
    public ResponseEntity<ResponseClient<JwtAuthenticationResponse>> signup(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    @Operation(summary = "Signin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error en el servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class)))})
    public ResponseEntity<ResponseClient<JwtAuthenticationResponse>> signin(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}

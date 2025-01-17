package com.utp.note.service;

import com.utp.note.helper.ResponseClient;
import com.utp.note.model.request.SignInRequest;
import com.utp.note.model.request.SignUpRequest;
import com.utp.note.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    ResponseClient<JwtAuthenticationResponse> signup(SignUpRequest request);

    ResponseClient<JwtAuthenticationResponse> signin(SignInRequest request);
}

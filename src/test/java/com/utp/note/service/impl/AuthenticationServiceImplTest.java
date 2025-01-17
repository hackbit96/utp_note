package com.utp.note.service.impl;

import com.utp.note.constant.Constant;
import com.utp.note.constant.Role;
import com.utp.note.domain.User;
import com.utp.note.model.ResponseClient;
import com.utp.note.model.request.SignInRequest;
import com.utp.note.model.request.SignUpRequest;
import com.utp.note.model.response.JwtAuthenticationResponse;
import com.utp.note.repository.UserRepository;
import com.utp.note.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        signUpRequest = new SignUpRequest("Orlando", "Camavilca", "orlando.camavilca@example.com", "password123");
        signInRequest = new SignInRequest("orlando.camavilca@example.com", "password123");
    }

    @Test
    void testSignup_userCamavilcasNotExist() {
        when(userRepository.findByEmail(signUpRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("mockToken");
        when(jwtService.generateToken(any(User.class))).thenReturn("mockToken");

        ResponseClient<JwtAuthenticationResponse> response = authenticationService.signup(signUpRequest);

        assertEquals("mockToken", response.getData().getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void testSignup_userAlreadyExists() {
        when(userRepository.findByEmail(signUpRequest.getEmail())).thenReturn(Optional.of(new User()));

        ResponseClient<JwtAuthenticationResponse> response = authenticationService.signup(signUpRequest);

        assertEquals(Constant.CODIGO_ERROR, response.getCodigo());
        assertEquals(String.format(Constant.MSG_USER_EXISTS, signUpRequest.getEmail()), response.getMensaje());
    }


    @Test
    void testSignin_success() {
        User user = User.builder()
                .firstName("Orlando")
                .lastName("Camavilca")
                .email("orlando.camavilca@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockToken");

        ResponseClient<JwtAuthenticationResponse> response = authenticationService.signin(signInRequest);

        assertEquals("mockToken", response.getData().getToken());
    }

    @Test
    void testSignin_badCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseClient<JwtAuthenticationResponse> response = authenticationService.signin(signInRequest);

        assertEquals(Constant.CODIGO_ERROR, response.getCodigo());
        assertEquals(Constant.MSG_INCORRECT_CREDENTIALS, response.getMensaje());
    }

    @Test
    void testSignin_userNotFound() {
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.empty());

        ResponseClient<JwtAuthenticationResponse> response = authenticationService.signin(signInRequest);

        assertEquals(Constant.CODIGO_ERROR, response.getCodigo());
        assertEquals(Constant.MSG_USER_NO_EXISTS, response.getMensaje());
    }

    @Test
    void testSignin_generalError() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("General error"));

        ResponseClient<JwtAuthenticationResponse> response = authenticationService.signin(signInRequest);

        assertEquals(Constant.CODIGO_ERROR, response.getCodigo());
        assertEquals(Constant.MSG_LOGIN_ERROR, response.getMensaje());
    }
}
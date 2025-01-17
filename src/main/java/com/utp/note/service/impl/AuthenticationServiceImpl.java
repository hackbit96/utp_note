package com.utp.note.service.impl;

import com.utp.note.constant.Constant;
import com.utp.note.constant.Role;
import com.utp.note.domain.User;
import com.utp.note.helper.ResponseClient;
import com.utp.note.model.request.SignInRequest;
import com.utp.note.model.request.SignUpRequest;
import com.utp.note.model.response.JwtAuthenticationResponse;
import com.utp.note.repository.UserRepository;
import com.utp.note.service.AuthenticationService;
import com.utp.note.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseClient<JwtAuthenticationResponse> signup(SignUpRequest request) {
        Optional<User> userExists = userRepository.findByEmail(request.getEmail());

        String msgUserExist = String.format(Constant.MSG_USER_EXISTS, request.getEmail());

        if (userExists.isPresent()) {
            return ResponseClient.setError(msgUserExist);
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        try {
            userRepository.save(user);
            return ResponseClient.setOk(JwtAuthenticationResponse.builder()
                    .token(jwtService.generateToken(user))
                    .build());
        } catch (DataIntegrityViolationException e) {
            log.info(msgUserExist);
            return ResponseClient.setError(msgUserExist);
        } catch (Exception e) {
            log.info(Constant.MSG_INTERNAL_SERVER_ERROR);
            return ResponseClient.setError(Constant.MSG_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseClient<JwtAuthenticationResponse> signin(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.MSG_USER_NO_EXISTS));

            return ResponseClient.setOk(JwtAuthenticationResponse.builder()
                    .token(jwtService.generateToken(user))
                    .build());

        } catch (BadCredentialsException ex) {
            return ResponseClient.setError(Constant.MSG_INCORRECT_CREDENTIALS);
        } catch (IllegalArgumentException ex) {
            return ResponseClient.setError(ex.getMessage());
        } catch (Exception ex) {
            return ResponseClient.setError(Constant.MSG_LOGIN_ERROR);
        }
    }
}

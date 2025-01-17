package com.utp.note.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utp.note.constant.Constant;
import com.utp.note.helper.ResponseClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String message;
        if (authException instanceof UsernameNotFoundException) {
            message = Constant.MSG_USER_NO_EXISTS;
        } else if (authException instanceof BadCredentialsException) {
            message = Constant.MSG_FORBIDDEN;
        } else {
            message = Constant.MSG_UNAUTHORIZED;
        }

        response.getWriter().write(
                new ObjectMapper().writeValueAsString(ResponseClient.setError(message))
        );
    }
}
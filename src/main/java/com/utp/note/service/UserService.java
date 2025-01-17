package com.utp.note.service;

import com.utp.note.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User getByEmail(String email);

}

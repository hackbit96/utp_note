package com.utp.note.repository;

import com.utp.note.domain.Note;
import com.utp.note.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(User user);

}

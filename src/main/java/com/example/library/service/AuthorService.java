package com.example.library.service;

import com.example.library.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AuthorService {
    List<Author> findAll();

    Author addAuthor(Author author);

    void deleteAuthor(long id);

    Optional<Author> findAuthorById(long id);
}

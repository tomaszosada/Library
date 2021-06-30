package com.example.library.controller;

import com.example.library.exception.CapacityException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.BookDto;
import com.example.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        try {
            authorService.addAuthor(author);
            return new ResponseEntity<>(author, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAuthors() {
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable(value = "id") int id){
        Optional<Author> author = authorService.findAuthorById(id);
        if(author.isPresent())
            return new ResponseEntity<>(author.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable(value = "id") int id){
        authorService.deleteAuthor(id);
    }
}

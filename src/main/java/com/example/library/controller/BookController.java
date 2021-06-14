package com.example.library.controller;


import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable(value = "id") int id){
        try {
            Book foundBook = bookService.findBookById(id);
            return foundBook;
        } catch (NoBookException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found", e);
        }


    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public  void addBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
        } catch (CapacityException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Too many books", e);
        }

    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoook(@PathVariable(value = "id") int id){
        try {
            bookService.deleteBook(id);
        } catch (NoBookException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found", e);
        }
    }
    @GetMapping("/books")
    public Map<Integer, Book> getAllBooks(){
        return bookService.findAll();
    }

}

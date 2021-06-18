package com.example.library.controller;


import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import com.example.library.model.BookDto;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//zmien na response entity
//dopisz testy gdzie mockujesz repo a nie service
@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private Book convertToEntity(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        System.out.println(book);
        return book;
    }
    private BookDto convertToDto(Book book){
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        System.out.println(bookDto);
        return bookDto;
    }


    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable(value = "id") int id) {
        try {
            Book foundBook = bookService.findBookById(id);
            return new ResponseEntity<>(convertToDto(foundBook), HttpStatus.OK);
        } catch (NoBookException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//filtracja request params
//on save action

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody BookDto bookDto) {
        try {
            Book book = convertToEntity(bookDto);
            Book savedBook = bookService.addBook(book);

            return convertToDto(savedBook);
        } catch (CapacityException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Too many books", e);
        }

    }


    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoook(@PathVariable(value = "id") int id) {
        try {
            bookService.deleteBook(id);
        } catch (NoBookException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found", e);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        Map<Integer, Book> books;
        books =  bookService.findAll();
        List<BookDto> booksDto = books.entrySet().stream().map(p -> convertToDto(p.getValue())).collect(Collectors.toList());
        return new ResponseEntity<>(booksDto, HttpStatus.OK);
    }

}

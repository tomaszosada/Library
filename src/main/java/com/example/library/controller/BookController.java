package com.example.library.controller;


import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import com.example.library.model.BookDto;
import com.example.library.service.BookService;
import com.example.library.service.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//dopisz testy gdzie mockujesz repo a nie service
@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    public BookController() {
        bookService = new BookServiceImpl();
    }

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private Book convertToEntity(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        System.out.println(book);
        return book;
    }

    private BookDto convertToDto(Book book) {
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

    @GetMapping("/book")
    public ResponseEntity<Map<Integer, Book>> getBooksBetweenYears(@RequestParam(name = "start") Optional<Integer> start,
                                                                   @RequestParam(name = "end") Optional<Integer> end) {
        return new ResponseEntity<>(bookService.findBookBetweenYears(start, end), HttpStatus.OK);
    }

    ;

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        try {
            Book book = convertToEntity(bookDto);
            Book savedBook = bookService.addBook(book);
            bookDto = convertToDto(savedBook);
            return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
        } catch (CapacityException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Map<Integer, BookDto>> getAllBooks(@RequestParam(name = "start") Optional<Integer> start,
                                                             @RequestParam(name = "end") Optional<Integer> end) {
        Map<Integer, Book> books;

        if(start.isPresent() || end.isPresent()){
            books = bookService.findBookBetweenYears(start, end);
        } else {
            books = bookService.findAll();
        }
        Map<Integer, BookDto> booksDto = new HashMap<>();
        for(Map.Entry<Integer, Book> entry : books.entrySet()){
            BookDto bookDto = convertToDto(entry.getValue());
            booksDto.put(entry.getKey(), bookDto);
        };

        return new ResponseEntity<>(booksDto, HttpStatus.OK);
    }

}

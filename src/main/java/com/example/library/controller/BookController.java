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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BookController {
    @Autowired
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

    private BookDto convertToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        System.out.println(bookDto);
        return bookDto;
    }


    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable(value = "id") int id) {
        Optional<Book> book = bookService.findBookById(id);
        if(book.isPresent()) {
            return new ResponseEntity<>(convertToDto(book.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book savedBook = bookService.addBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (CapacityException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable(value = "id") int id) {
        try {
            bookService.deleteBook(id);
        } catch (NoBookException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book not found", e);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<List< BookDto>> getAllBooks(@RequestParam(name = "start") Optional<String> start,
                                                             @RequestParam(name = "end") Optional<String> end) {


        List<Book> books;
        books = bookService.findBookBetweenPublicationDates(start, end);

        List<BookDto> bookDtos = new ArrayList<>();
        for(Book book : books){
            bookDtos.add(convertToDto(book));
        }
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

}

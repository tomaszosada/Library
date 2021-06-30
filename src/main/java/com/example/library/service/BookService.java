package com.example.library.service;

import com.example.library.model.Book;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public interface BookService {
    List<Book> findAll();
    List<Book> findBookBetweenPublicationDates(Optional <String> startDate, Optional <String> endDate);
    Book addBook(Book book);
    void deleteBook(long id);
    Optional<Book> findBookById(long id);
    void deleteAllBooks();
}

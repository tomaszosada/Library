package com.example.library.service;

import com.example.library.exception.CapacityException;
import com.example.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface BookService {
    Map<Integer, Book> findAll();
    Map<Integer, Book> findBookBetweenYears(Optional <Integer> startYear, Optional <Integer> endYear);
    Book addBook(Book book);
    void deleteBook(int id);
    Book findBookById(int id);
    void deleteAllBooks();
}

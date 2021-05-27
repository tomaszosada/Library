package com.example.library.service;

import com.example.library.exception.CapacityException;
import com.example.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BookService {
    Map<Integer, Book> findAll();
    Book addBook(Book book);
    void deleteBook(Integer id);
    Book findBookById(Integer id);
    void deleteAllBooks();
}

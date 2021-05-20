package com.example.library.service;

import com.example.library.exception.CapacityException;
import com.example.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

public interface BookService {
    HashMap<Integer, Book> findAll();
    Book addBook(Book book);
    void deleteBook(Integer id);
    Book findBookById(Integer id);
    void deleteAllBooks();
}

package com.example.library.repository;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public interface BookRepository {
    Map<Integer, Book> findAll();
    Book addBook(Book book);
    void deleteBook(int id);
    Book findBookById(int id);
    void deleteAllBooks();

}

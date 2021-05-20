package com.example.library.repository;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;


@Repository
public interface BookRepository {
    HashMap<Integer, Book> findAll();
    Book addBook(Book book) throws CapacityException;
    void deleteBook(Integer id) throws NoBookException;
    Book findBookById(Integer id) throws NoBookException;
    void deleteAllBooks();

}

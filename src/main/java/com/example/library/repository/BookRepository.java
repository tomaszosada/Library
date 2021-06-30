package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;


@Repository
public interface BookRepository {
    Map<Integer, Book> findAll();
    Book addBook(Book book);
    void deleteBook(int id);
    Book findBookById(int id);
    Map<Integer, Book> findBookBetweenYears(Optional<Integer> startYear, Optional<Integer> endYear);
    void deleteAllBooks();

}

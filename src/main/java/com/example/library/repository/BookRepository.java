package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public interface BookRepository {
    Map<Integer, Book> findAll();
    Book addBook(Book book);
    void deleteBook(int id);
    Book findBookById(int id);
    Map<Integer, Book> findBookBetweenYears(int startYear, int endYear);
    void deleteAllBooks();

}

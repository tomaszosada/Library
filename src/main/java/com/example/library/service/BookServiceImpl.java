package com.example.library.service;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
    }

    @Override
    public Map<Integer, Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        try {
            return repository.addBook(book);
        } catch (CapacityException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public void deleteBook(Integer id) {
        try {
            repository.deleteBook(id);
        } catch (NoBookException e) {
            log.error(e.toString());
        }
    }

    @Override
    public Book findBookById(Integer id) {
        try {
            return repository.findBookById(id);
        } catch (NoBookException e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public void deleteAllBooks() {
        repository.deleteAllBooks();
    }
}

package com.example.library.service;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
        logger.info(bookRepository.findAll().keySet().toString());
    }


    @Override
    public HashMap<Integer, Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        try {
            Book savedBook = repository.addBook(book);
            return savedBook;
        } catch (CapacityException e) {
            logger.info(e.toString());
            return null;
        }
    }

    @Override
    public void deleteBook(Integer id) {
        try {
            repository.deleteBook(id);
        } catch (NoBookException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public Book findBookById(Integer id) {
        try {
            return repository.findBookById(id);
        } catch (NoBookException e) {
            logger.info(e.toString());
            return null;
        }
    }

    @Override
    public void deleteAllBooks() {
        repository.deleteAllBooks();
    }
}

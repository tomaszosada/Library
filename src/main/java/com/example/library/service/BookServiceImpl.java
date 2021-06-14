package com.example.library.service;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private int capacity;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, @Value("${capacity}") int capacity) {
        bookRepository.addBook(new Book("Title", "Author"));
        this.repository = bookRepository;
        this.capacity = capacity;

    }

    @Override
    public Map<Integer, Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book addBook(Book book) {
            if (repository.findAll().size() < capacity) {
                return repository.addBook(book);
            } else {
                throw new CapacityException("Too many books");
            }


    }

    @Override
    public void deleteBook(int id) {
        if(repository.findAll().containsKey(id)) {
            repository.deleteBook(id);
        } else {
            throw new NoBookException("No book found!");
        }

    }

    @Override
    public Book findBookById(int id) {
        if(repository.findAll().containsKey(id)){
            return repository.findBookById(id);
        } else {
            throw new NoBookException("No book found!");
        }
    }

    @Override
    public void deleteAllBooks() {
        repository.deleteAllBooks();
    }
}

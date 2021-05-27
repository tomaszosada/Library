package com.example.library.repository;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("bookRepository")
public class BookRepositoryImpl implements BookRepository {
    private HashMap<Integer, Book> books;



    @Autowired
    public BookRepositoryImpl(HashMap<Integer, Book> books) {
        this.books = books;
    }

    public BookRepositoryImpl() {
        this.books = new HashMap<>();
    }

    @Override
    public Map<Integer, Book> findAll() {
        return books;
    }

    @Override
    public Book addBook(Book book) throws CapacityException {
            books.put(books.size(), book);
            return book;
    }

    @Override
    public void deleteBook(int id) throws NoBookException {
        if(books.containsKey(id)){
            books.remove(id);
        } else {
            throw new NoBookException("There's no book with that id");
        }
    }

    @Override
    public Book findBookById(int id) throws NoBookException {
        if(books.containsKey(id)){
            return books.get(id);
        } else {
            throw new NoBookException("There's no book with that id");
        }
    }

    @Override
    public void deleteAllBooks() {
        books = new HashMap<>();
    }

}

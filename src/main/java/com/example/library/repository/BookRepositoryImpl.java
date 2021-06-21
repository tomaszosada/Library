package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
    public Book addBook(Book book){
            books.put(books.size(), book);
            return book;
    }

    @Override
    public void deleteBook(int id){
            books.remove(id);
    }

    @Override
    public Book findBookById(int id){
        return books.get(id);

    }

    @Override
    public Map<Integer, Book> findBookBetweenYears(Optional<Integer> startYear, Optional<Integer> endYear) {
        if(startYear.isPresent() && endYear.isPresent()) {
            return books.entrySet().stream().filter(b -> Objects.nonNull(b.getValue().getYear())).
                    filter(b -> b.getValue().getYear() > startYear.get() && b.getValue().getYear() < endYear.get())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        if(startYear.isPresent()){
            return books.entrySet().stream().filter(b -> Objects.nonNull(b.getValue().getYear())).
                    filter(b -> b.getValue().getYear() > startYear.get())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            return books.entrySet().stream().filter(b -> Objects.nonNull(b.getValue().getYear())).
                    filter(b -> b.getValue().getYear() < endYear.get())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    @Override
    public void deleteAllBooks() {
        books = new HashMap<>();
    }

}

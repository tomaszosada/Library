package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Log4j2
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    private final BookRepository repository;
    @Autowired
    private final AuthorRepository authorRepository;
    private int capacity;


    @Autowired
    public BookServiceImpl(BookRepository bookRepository,AuthorRepository authorRepository, @Value("${capacity}") int capacity) {
        this.authorRepository = authorRepository;
        this.repository = bookRepository;
        this.capacity = capacity;

    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Book> findBookBetweenPublicationDates(Optional<String> start, Optional<String> end) {
        if(start.isPresent() && end.isPresent()) {
            LocalDate startDate = LocalDate.parse(start.get());
            LocalDate endDate = LocalDate.parse(end.get());
            return repository.findAllByPublicationDateLessThanEqualAndPublicationDateGreaterThanEqual(startDate, endDate);
        } else if(start.isPresent()) {
            LocalDate startDate = LocalDate.parse(start.get());
            return repository.findAllByPublicationDateGreaterThan(startDate);
        }
        else if(end.isPresent()){
            LocalDate endDate = LocalDate.parse(end.get());
            return repository.findAllByPublicationDateLessThanEqual(endDate);
        } else {
            return repository.findAll();
        }
    }
    @Transactional
    @Override
    public Book addBook(Book book) {

        Author author = book.getAuthor();
        Optional<Author> authorOptional = authorRepository.findAuthorByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        if(authorOptional.isPresent()){

            author = authorOptional.get();
            List<Book> books = author.getBooks();
            books.add(book);
            author.setBooks(books);
            book.setAuthor(author);
            Book savedBook = repository.save(book);
            return savedBook;

        } else {

            Book savedBook = repository.save(book);
            return savedBook;
        }

    }


    @Override
    public void deleteBook(long id) {

        repository.deleteById(id);

    }

    @Override
    public Optional<Book> findBookById(long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteAllBooks() {
        repository.deleteAll();
    }
}

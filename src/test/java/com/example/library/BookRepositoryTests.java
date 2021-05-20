package com.example.library;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


import com.example.library.exception.NoBookException;

import com.example.library.exception.CapacityException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.util.ReflectionTestUtils;

public class BookRepositoryTests {
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() throws CapacityException {
        bookRepository = new BookRepositoryImpl();
        ReflectionTestUtils.setField(bookRepository, "capacity", 3);
        String title = "Test book title";
        String author ="No Idea";
        Book book = new Book(title, author);

        bookRepository.addBook(book);
    }

    @Test
    void addBookTest() throws CapacityException {
        String title = "Test book title";
        String author ="No Idea";
        Book book = new Book(title, author);

        Book addedBook = bookRepository.addBook(book);
        assertThat(addedBook.toString()).isEqualTo(book.toString());
    }

    @Test
    void getBookByIdTest() throws NoBookException {
        int id = 0;
        Book book = bookRepository.findBookById(0);
        assertThat(book.getTitle()).isNotNull().isEqualTo("Test book title");
    }

    @Test
    void deleteBook() throws NoBookException {
        int id = 0;
        int size = bookRepository.findAll().size();

        bookRepository.deleteBook(id);
        assertThat(bookRepository.findAll().size()).isEqualTo(size-1);
    }
    @Test
    void capacityOverloadTest() {
        assertThatThrownBy(() -> {
            bookRepository.addBook(new Book("Third", "ThirdAuthor"));
            bookRepository.addBook(new Book("Third", "ThirdAuthor"));
            bookRepository.addBook(new Book("Third", "ThirdAuthor"));

        }).isInstanceOf(CapacityException.class);
    }
    @Test
    void wrongBookDeleteTest(){
        int id = bookRepository.findAll().size() +1;
        assertThatThrownBy(() -> {
            bookRepository.deleteBook(id);
        }).isInstanceOf(NoBookException.class);
    }
    @Test
    void findWrongBookByIdTest(){
        int id = bookRepository.findAll().size() +1;
        assertThatThrownBy(() -> {
            bookRepository.findBookById(id);
        }).isInstanceOf(NoBookException.class);

    }
    @Test
    void deleteAllBooksTest() throws CapacityException {
        String title = "Title";
        String author = "Author";
        Book book = new Book(title, author);
        bookRepository.addBook(book);
        bookRepository.deleteAllBooks();
        assertThat(bookRepository.findAll().size()).isEqualTo(0);
    }
}

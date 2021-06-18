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

import java.util.HashMap;
import java.util.Map;

class BookRepositoryTests {
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() throws CapacityException {
        bookRepository = new BookRepositoryImpl();
//        ReflectionTestUtils.setField(bookRepository, "capacity", 3);
        String title = "Test book title";
        String author ="No Idea";
        Book book = new Book(title, author);
        bookRepository.addBook(book);

    }

    @Test
    void findBetween(){
        //given
        Book bookToAdd = new Book("title", "Author");
        bookToAdd.setYear(2010);
        bookRepository.addBook(bookToAdd);
        int start = 1990;
        int end = 2020;
        //when
        Map<Integer, Book> books = bookRepository.findBookBetweenYears(start, end);
        assertThat(books.containsValue(bookToAdd));

    }

    @Test
    void addBookTest() throws CapacityException {
        //given
        String title = "Test book title";
        String author ="No Idea";

        Book book = new Book(title, author);
        //when
        Book addedBook = bookRepository.addBook(book);
        //then
        assertThat(addedBook).isEqualTo(book);
    }

    @Test
    void getBookByIdTest() {
        //given
        int id = 0;
        //when
        Book book = bookRepository.findBookById(0);
        //then
        assertThat(book.getTitle()).isNotNull().isEqualTo("Test book title");
    }

    @Test
    void deleteBook() throws NoBookException {
        //given
        int id = 0;
        int size = bookRepository.findAll().size();
        //when
        bookRepository.deleteBook(id);
        //then
        assertThat(bookRepository.findAll().size()).isEqualTo(size-1);
    }

    @Test
    void deleteAllBooksTest() throws CapacityException {
        String title = "Title";
        String author = "Author";
        Book book = new Book(title, author);
        bookRepository.addBook(book);
        bookRepository.deleteAllBooks();
        assertThat(bookRepository.findAll().size()).isZero();
    }
}

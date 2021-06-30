package com.example.library;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    private BookService bookService;
    private AuthorRepository authorRepository;
    @BeforeEach
    void setUp() {
         bookService = new BookServiceImpl(bookRepository, authorRepository, 3);
    }

    @Test
     void addBookTest() throws CapacityException {
        //given
        String title = "Test title";
        Author author = new Author();
        author.setLastName("Last");
        author.setFirstName("First");
        Book book = new Book(title, author);
        //when
        when(bookRepository.save(any(Book.class))).then(returnsFirstArg());
        Book savedBook = bookService.addBook(book);
        //then
        assertThat(savedBook).isEqualTo(book);
    }

    @Test
    void deleteBookTest() throws NoBookException {
        //given
        int id = 0;
        Map<Integer, Book> books = new HashMap<Integer, Book>();
        Book book = new Book("Title", "Author");
        books.put(id, book);
        //when
        when(bookRepository.findAll()).thenReturn(books);
        bookService.deleteBook(id);
        //then
        verify(bookRepository, times(1)).deleteBook(id);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findBookByIdTest() {
        //given
        int id = 0;
        Map<Integer, Book> books = new HashMap<Integer, Book>();
        Book book = new Book("Title", "Author");
        books.put(0, book);
        //when
        when(bookRepository.findAll()).thenReturn(books);
        when(bookRepository.findBookById(any(Integer.class))).thenReturn(book);
        Book foundBook = bookService.findBookById(id);
        //then
        assertThat(foundBook).isEqualTo(book);
    }

    @Test()
    void noBookFoundTest() throws NoBookException {
        int id = 0;
        assertThrows(NoBookException.class, () -> {
            bookService.findBookById(id);
        });

    }

    @Test
    void findAllTest() {
        Map<Integer, Book> books = new HashMap<Integer, Book>();
        books.put(0, new Book("title", "author"));
        books.put(1, new Book("title", "author"));

        when(bookRepository.findAll()).thenReturn(books);
        Map<Integer, Book> foundBooks = bookService.findAll();
        assertThat(foundBooks.size()).isEqualTo(books.size());

    }
}

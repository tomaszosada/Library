package com.example.library;

import com.example.library.exception.CapacityException;
import com.example.library.exception.NoBookException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookRepositoryImpl;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
//verify czyli sprawdz czy metoda zostala wywolana
//spraddz wyjatki
//Inity w before each
//do throw when do wyjatkow

@ExtendWith(MockitoExtension.class)
class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    private BookService bookService;
    @BeforeEach
    void setUp() {
         bookService = new BookServiceImpl(bookRepository);
    }

    @Test
     void addBookTest() throws CapacityException {
        String title = "Test title";
        String author = "Test author";
        Book book = new Book(title, author);
        when(bookRepository.addBook(any(Book.class))).then(returnsFirstArg());
        Book savedBook = bookService.addBook(book);
        assertThat(savedBook).isEqualTo(book);
    }

    @Test
    void deleteBookTest() throws NoBookException {
        int id = 0;
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(bookRepository).deleteBook(valueCapture.capture());
        bookService.deleteBook(id);
        assertThat(valueCapture.getValue()).isEqualTo(id);
    }

//    @Test
//    void deleteWrongBookTest() throws NoBookException{
//        int id= 1;
//        String title = "Test title";
//        String author = "Test author";
//        Book book = new Book(title, author);
//
//        doThrow(new NoBookException("Book not found")).when(bookRepository).deleteBook(any(Integer.class));
//
//    }
    @Test
    void findBookByIdTest() throws NoBookException {
        int id = 0;
        Book book = new Book("Title", "Author");
        when(bookRepository.findBookById(any(Integer.class))).thenReturn(book);
        Book foundBook = bookService.findBookById(id);
        assertThat(foundBook).isEqualTo(book);
    }

    @Test()
    void noBookFoundTest() throws NoBookException {
        int id = 0;
        doThrow(new NoBookException("Book not found")).when(bookRepository).findBookById(any(Integer.class));
        Book foundBook = bookService.findBookById(id);
        assertThat(foundBook).isNull();
    }

    @Test
    void findAllTest() {
        HashMap<Integer, Book> books = new HashMap<>();
        books.put(0, new Book("title", "author"));
        books.put(1, new Book("title", "author"));

        when(bookRepository.findAll()).thenReturn(books);
        HashMap<Integer, Book> foundBooks = (HashMap<Integer, Book>) bookService.findAll();
        assertThat(foundBooks.size()).isEqualTo(books.size());

    }
}

package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTests {
    @MockBean
    BookService bookService;
    @Autowired
    MockMvc mockMvc;


    Map<Integer, Book> books;
    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    public void init() {
        books = new HashMap<>();
        Book book1 = new Book("Title", "Author");
        Book book2 = new Book("Title2", "Author2");
        books.put(0, book1);
        books.put(1, book2);

    }


    @Test
    void getBooksTest() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("0.title", is("Title")))
                .andExpect(jsonPath("1.title", is("Title2")));
        verify(bookService, times(1)).findAll();

    }

    @Test
    void addBookTest() throws Exception {


        Book book3 = new Book("Title3", "Author3");
        when(bookService.addBook(any(Book.class))).then(returnsFirstArg());
        this.mockMvc.perform(post("/book").content(mapper.writeValueAsString(book3)).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Title3")));
        verify(bookService, times(1)).addBook(book3);

    }

    @Test
    void getBookByIdTest() throws Exception {
        int id = 0;
        when(bookService.findBookById(id)).thenReturn(books.get(id));
        this.mockMvc.perform(get("/book/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Title")));
        verify(bookService, times(1)).findBookById(id);

    }

    @Test
    void deleteBookTest() throws Exception {
        int id = 0;
        this.mockMvc.perform(delete("/book/" + id)).andExpect(status().isOk());
        verify(bookService, times(1)).deleteBook(id);

    }
}

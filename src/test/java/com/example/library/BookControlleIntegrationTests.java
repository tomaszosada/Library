package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
public class BookControlleIntegrationTests {
    @MockBean
    BookRepository bookRepository;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void addBookTest(){
        //given
        Book book = new Book("Lee", "Moncello");
        //when
        when(bookRepository.addBook(any(Book.class))).thenReturn(null);
    }

}

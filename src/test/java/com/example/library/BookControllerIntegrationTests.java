package com.example.library;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerIntegrationTests {
    @MockBean
    BookRepository bookRepository;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;
    Map<Integer, Book> books;

    @BeforeEach
    public void init() {
        books = new HashMap<>();
        Author author = new Author();
        author.setFirstName("Initaut");
        author.setLastName("Initlast");
        Book book1 = new Book("Title", "Author");
        Book book2 = new Book("Title2", "Author2");
        books.put(0, book1);
        books.put(1, book2);

    }


    @Test
    void getBooksTest() throws Exception {
        //when
        when(bookRepository.findAll()).thenReturn(books);
        //then
        this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("0.title", is("Title")))
                .andExpect(jsonPath("1.title", is("Title2")));
        verify(bookRepository, times(1)).findAll();

    }

    @Test
    void addBookTest() throws Exception {

        //given
        Book book3 = new Book("Title3", "Author3");
        //when
        when(bookRepository.addBook(any(Book.class))).then(returnsFirstArg());
        //then
        this.mockMvc.perform(post("/book").content(mapper.writeValueAsString(book3)).contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Title3")));
        verify(bookRepository, times(1)).addBook(book3);

    }

    @Test
    void getBookByIdTest() throws Exception {
        //given
        int id = 0;
        //when
        when(bookRepository.findBookById(id)).thenReturn(books.get(id));
        when(bookRepository.findAll()).thenReturn(books);
        //then
        this.mockMvc.perform(get("/book/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Title")));
        verify(bookRepository, times(1)).findBookById(id);
        verify(bookRepository, times(1)).findAll();


    }

    @Test
    void deleteBookTest() throws Exception {
        //given
        int id = 0;
        //when
        when(bookRepository.findAll()).thenReturn(books);
        //then
        this.mockMvc.perform(delete("/book/" + id)).andExpect(status().isOk());
        verify(bookRepository, times(1)).deleteBook(id);
        verify(bookRepository, times(1)).findAll();


    }

    @Test
    void getBetweenTest() throws Exception {
        //given
        Optional<Integer> startYear = Optional.of(1900);
        Optional<Integer> endYear = Optional.of(2020);
        books.remove(0);
        //when
        when(bookRepository.findBookBetweenYears(startYear, endYear)).thenReturn(books);
        //then
        this.mockMvc.perform(get("/books?start=" + startYear.get() + "&end=" + endYear.get())).andExpect(status().isOk()).
                andExpect(jsonPath("1.title", is("Title2")));

        verify(bookRepository, times(1)).findBookBetweenYears(startYear, endYear);

    }

}

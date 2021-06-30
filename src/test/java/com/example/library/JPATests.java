package com.example.library;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.BookType;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
class JPATests {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp(){
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Last");
        LocalDate date = LocalDate.now();
        Book book = new Book("Boooookssss", author, BookType.DRAMA, date);
        bookRepository.save(book);
    }
    @Test
    void addBookTest(){
        //given
        Author author = new Author();
        author.setFirstName("Second");
        author.setLastName("Last");
        LocalDate date = LocalDate.now();
        Book book = new Book("Thaht added book", author, BookType.DRAMA, date);
        //when
        bookRepository.save(book);
        //then
        assertThat(bookRepository.findAll().size()).isEqualTo(2);
        assertThat(bookRepository.findAll().get(1)).isEqualTo(book);
        assertThat(authorRepository.findAll().get(1)).isEqualTo(author);
    }

    @Test
    void deleteBookTest(){
        //given
        long id = bookRepository.findAll().get(0).getId();
        //when
        bookRepository.deleteById(id);
        //then
        assertThat(bookRepository.findAll().isEmpty()).isTrue();
    }

    @Test
    void getBookByIdTest(){
        //given
        long id = bookRepository.findAll().get(0).getId();
        //when
        Book book = bookRepository.findById(id).get();
        //then
        assertThat(book.getTitle()).isEqualTo("Boooookssss");
    }

    @Test
    void addAuthorTest(){
        //given
        Author author = new Author();
        author.setFirstName("Mario");
        author.setLastName("Luigi");
        //when
        Author savedAuthor = authorRepository.save(author);
        //then
        assertThat(savedAuthor).isEqualTo(author);
        assertThat(authorRepository.findById(2l).get()).isEqualTo(author);
    }

    @Test
    void deleteAuthorTest(){
        //given
        long id = authorRepository.findAll().get(0).getId();
        //when
        authorRepository.deleteById(id);
        //then
        assertThat(authorRepository.findAll().isEmpty()).isTrue();
    }
}

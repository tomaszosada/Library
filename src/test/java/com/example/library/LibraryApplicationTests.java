package com.example.library;

import com.example.library.exception.CapacityException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.service.BookServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
class LibraryApplicationTests {

	@Autowired
	BookService bookService;


	@AfterEach
	public void cleanUp() {
		bookService.deleteAllBooks();
	}
	@Test
	void add(){
		//given
		Book book1 = new Book("title1", "author1");
		//when
		bookService.addBook(book1);
		//then
		assertThat(bookService.findAll().size()).isEqualTo(1);
		assertThat(bookService.findBookById(0)).isEqualTo(book1);


//

	}

	static Stream<Arguments> booksProvider() {
		return Stream.of(
				arguments("first", "first"),
				arguments("Second", "Second")
				);
	}
	@ParameterizedTest
	@MethodSource("booksProvider")
	 void addTest(String title, String author) {

		bookService.addBook(new Book(title, author));
		int len = bookService.findAll().size();
		assertThat(bookService.findAll().get(len-1).getTitle()).isEqualTo(title);
	}

	//delete
	@Test
	void deleteTest() {
		//given
		Book book = new Book("Book", "ToDelete");
		bookService.addBook(book);
		int len = bookService.findAll().size();
		int id = 0;
		//when
		bookService.deleteBook(id);
		//then
		assertThat(bookService.findAll().size()).isEqualTo(len-1);
	}

	@Test
	void findByIdTest() {
		//given
		int id = 0;
		Book book = new Book("title", "author");
		bookService.addBook(book);
		//when
		Book foundBook = bookService.findBookById(id);
		//then
		assertThat(foundBook.getTitle()).isEqualTo("title");

	}

}

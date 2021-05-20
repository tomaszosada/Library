package com.example.library;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);

//		BookService service = appContext.getBean("bookService", BookService.class);
//		System.out.println(service.findAll().get(0).getAuthor());
//		service.addBook(new Book("test", "test"));
//		System.out.println(service.findAll());
//		service.deleteBook(0);
//		System.out.println(service.findAll());
	}

}

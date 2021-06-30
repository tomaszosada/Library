package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LibraryApplication {
//	postgres albo h2
//	relacje / podziel zeby osobno byl autor
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);

	}

}

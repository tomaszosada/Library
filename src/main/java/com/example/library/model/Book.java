package com.example.library.model;

import lombok.Data;
@Data
public class Book {
    private final String title;
    private final String author;
    private final Integer year;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.year = null;
    }
}

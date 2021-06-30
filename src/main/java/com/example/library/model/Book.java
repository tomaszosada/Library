package com.example.library.model;

import lombok.Data;
@Data
public class Book {
    private String title;
    private String author;
    private Integer year;
    public Book(){};
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.year = null;
    }
}

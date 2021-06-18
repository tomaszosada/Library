package com.example.library.model;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String author;
    private Integer year;
    public BookDto(){};
    public BookDto(String title, String author) {
        this.title = title;
        this.author = author;
        this.year = null;
    }
}

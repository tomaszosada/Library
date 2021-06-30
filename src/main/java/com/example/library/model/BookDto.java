package com.example.library.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDto {
    private String title;
    private String author;
    private LocalDate date;
    public BookDto(){};
    public BookDto(String title, String author, LocalDate date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }
}

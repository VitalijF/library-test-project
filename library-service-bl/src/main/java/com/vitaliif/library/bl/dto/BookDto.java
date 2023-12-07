package com.vitaliif.library.bl.dto;

import org.springframework.stereotype.Component;

public class BookDto {


    private Integer id;
    private String title;
    private String author;
    private String genre;
    private String publisher;

    public Integer getId() {
        return id;
    }

    public BookDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public BookDto setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookDto setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }
}

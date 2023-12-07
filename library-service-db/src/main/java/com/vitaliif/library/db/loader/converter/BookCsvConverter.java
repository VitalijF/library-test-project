package com.vitaliif.library.db.loader.converter;

import com.vitaliif.library.db.entity.Author;
import com.vitaliif.library.db.entity.Book;
import com.vitaliif.library.db.entity.Genre;
import com.vitaliif.library.db.entity.Publisher;
import com.vitaliif.library.db.loader.dto.BookCsvDto;
import org.springframework.stereotype.Component;

@Component
public class BookCsvConverter {

    public Book convert(BookCsvDto bookCsvDto) {
        Book book = new Book();
        book.setName(bookCsvDto.getTitle());
        book.setAuthor(convertAuthor(bookCsvDto));
        book.setGenre(convertGenre(bookCsvDto));
        book.setPublisher(convertPublisher(bookCsvDto));

        return book;
    }

    private Publisher convertPublisher(BookCsvDto bookCsvDto) {
        Publisher publisher = new Publisher();
        publisher.setName(bookCsvDto.getGenre());

        return publisher;
    }

    private Genre convertGenre(BookCsvDto bookCsvDto) {
        Genre genre = new Genre();
        genre.setName(bookCsvDto.getGenre());

        return genre;
    }

    private Author convertAuthor(BookCsvDto bookCsvDto) {
        Author author = new Author();
        author.setName(bookCsvDto.getAuthor());

        return author;
    }
}

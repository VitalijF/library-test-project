package com.vitaliif.library.bl.converter;

import com.vitaliif.library.bl.dto.BookDto;
import com.vitaliif.library.db.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public BookDto covert(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setAuthor(book.getAuthor().getName())
                .setTitle(book.getName())
                .setGenre(book.getGenre().getName())
                .setPublisher(book.getPublisher().getName());
    }
}

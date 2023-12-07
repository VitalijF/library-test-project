package com.vitaliif.library.db.loader;

import com.vitaliif.library.db.entity.Author;
import com.vitaliif.library.db.entity.Book;
import com.vitaliif.library.db.entity.Genre;
import com.vitaliif.library.db.entity.Publisher;
import com.vitaliif.library.db.loader.converter.BookCsvConverter;
import com.vitaliif.library.db.loader.dto.BookCsvDto;
import com.vitaliif.library.db.parser.CsvParser;
import com.vitaliif.library.db.repo.AuthorRepository;
import com.vitaliif.library.db.repo.BookRepository;
import com.vitaliif.library.db.repo.GenreRepository;
import com.vitaliif.library.db.repo.PublisherRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("bookCsvDataLoader")
public class BookCsvDataLoader implements DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookCsvDataLoader.class);

    private final CsvParser csvParser;
    private final PublisherRepository publisherRepository;

    private final BookCsvConverter bookCsvConverter;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookCsvDataLoader(CsvParser csvParser, PublisherRepository publisherRepository,
                             BookCsvConverter bookCsvConverter, GenreRepository genreRepository,
                             AuthorRepository authorRepository, BookRepository bookRepository) {
        this.csvParser = csvParser;
        this.publisherRepository = publisherRepository;
        this.bookCsvConverter = bookCsvConverter;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    @Transactional
    public void loadDataIntoTable(String filePath) {
        List<BookCsvDto> booksFromCsv = csvParser.parse(BookCsvDto.class, filePath);
        List<Book> books = booksFromCsv.stream().map(bookCsvConverter::convert).toList();

        books.forEach(this::saveBook);
    }

    private void saveBook(Book book) {
        Optional<Book> existedBook = bookRepository.findBookByName(book.getName());
        if (existedBook.isPresent()) {
            LOGGER.warn("Book with name {} already exist", book.getName());
        } else {
            Optional<Genre> existedGenre = genreRepository.findGenreByName(book.getGenre().getName());
            existedGenre.ifPresentOrElse(book::setGenre, () -> genreRepository.save(book.getGenre()));

            Optional<Author> existedAuthor = authorRepository.findAuthorByName(book.getAuthor().getName());
            existedAuthor.ifPresentOrElse(book::setAuthor, () -> authorRepository.save(book.getAuthor()));

            Optional<Publisher> existedPublisher = publisherRepository.findPublisherByName(book.getPublisher().getName());
            existedPublisher.ifPresentOrElse(book::setPublisher, () -> publisherRepository.save(book.getPublisher()));

            bookRepository.save(book);
        }
    }
}

package com.vitaliif.library.db.loader;

import com.vitaliif.library.db.entity.Book;
import com.vitaliif.library.db.entity.Borrowed;
import com.vitaliif.library.db.entity.User;
import com.vitaliif.library.db.loader.converter.BorrowedCsvConverter;
import com.vitaliif.library.db.loader.dto.BorrowedCsvDto;
import com.vitaliif.library.db.parser.CsvParser;
import com.vitaliif.library.db.repo.BookRepository;
import com.vitaliif.library.db.repo.BorrowedRepository;
import com.vitaliif.library.db.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("borrowedCsvDataLoader")
public class BorrowedCsvDataLoader implements DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowedCsvDataLoader.class);

    private final CsvParser csvParser;
    private final BorrowedCsvConverter borrowedCsvConverter;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowedRepository borrowedRepository;

    public BorrowedCsvDataLoader(CsvParser csvParser,
                                 BorrowedCsvConverter borrowedCsvConverter,
                                 BookRepository bookRepository,
                                 UserRepository userRepository,
                                 BorrowedRepository borrowedRepository) {
        this.csvParser = csvParser;
        this.borrowedCsvConverter = borrowedCsvConverter;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowedRepository = borrowedRepository;
    }

    @Override
    @Transactional
    public void loadDataIntoTable(String filePath) {
        List<BorrowedCsvDto> borrowedCsvDtos = csvParser.parse(BorrowedCsvDto.class, filePath);
        List<Borrowed> borrowedList = borrowedCsvDtos.stream().map(borrowedCsvConverter::convert).toList();

        borrowedList.forEach(this::saveBorrowed);
    }

    private void saveBorrowed(Borrowed borrowed) {
        Optional<Book> existedBook = bookRepository.findBookByName(borrowed.getBook().getName());
        Optional<User> existedUser = userRepository.findUserByFirstNameAndLastName(
                borrowed.getUser().getFirstName(),
                borrowed.getUser().getLastName()
        );

        if (existedBook.isEmpty() || existedUser.isEmpty()) {
            LOGGER.error("For borrowed saving user and book must exist");
            return;
        }

        Optional<Borrowed> existedBorrowed = borrowedRepository.findBorrowedByBookAndUser(existedBook.get(), existedUser.get());
        if (existedBorrowed.isPresent()) {
            LOGGER.warn("Borrowed is already exist for user {} and book {}", borrowed.getUser().getFirstName(), borrowed.getBook().getName());
            return;
        }

        borrowed.setUser(existedUser.get());
        borrowed.setBook(existedBook.get());

        borrowedRepository.save(borrowed);
    }
}

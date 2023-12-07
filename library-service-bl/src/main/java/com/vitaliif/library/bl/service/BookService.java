package com.vitaliif.library.bl.service;

import com.vitaliif.library.bl.converter.BookConverter;
import com.vitaliif.library.bl.dto.BookDto;
import com.vitaliif.library.db.entity.Book;
import com.vitaliif.library.db.entity.Borrowed;
import com.vitaliif.library.db.entity.User;
import com.vitaliif.library.db.repo.BookRepository;
import com.vitaliif.library.db.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookConverter bookConverter;

    public BookService(BookRepository bookRepository,
                       UserRepository userRepository,
                       BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookConverter = bookConverter;
    }

    @Transactional
    public List<BookDto> getAllAvailableBooks() {
        final List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .filter(book -> isBookAvailable(book.getBorrowedList()))
                .map(bookConverter::covert)
                .toList();
    }

    @Transactional
    public List<BookDto> getBorrowedBooks(Integer userId, LocalDate startDate, LocalDate endDate) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User is not exist");
        }

        final List<Borrowed> borrowedList = user.get().getBorrowedList();
        final List<Book> books = findBooksBorrowedInDateRange(borrowedList, startDate, endDate);

        return books.stream().map(bookConverter::covert).collect(Collectors.toList());
    }

    private List<Book> findBooksBorrowedInDateRange(List<Borrowed> borrowedList, LocalDate startDate, LocalDate endDate) {
        if (CollectionUtils.isEmpty(borrowedList)) {
            return List.of();
        }
        return borrowedList.stream()
                .filter(b -> !b.getStartAt().isBefore(startDate) && !b.getStartAt().isAfter(endDate))
                .map(Borrowed::getBook)
                .collect(Collectors.toList());
    }

    private boolean isBookAvailable(List<Borrowed> borrowedList) {
        if (CollectionUtils.isEmpty(borrowedList)) {
            return true;
        }
        return borrowedList.stream().allMatch(f -> f.getEndAt() != null && LocalDate.now().isAfter(f.getEndAt()));
    }
}

package com.vitaliif.library.bl.service;

import com.vitaliif.library.bl.converter.BookConverter;
import com.vitaliif.library.bl.dto.BookDto;
import com.vitaliif.library.db.entity.Book;
import com.vitaliif.library.db.entity.Borrowed;
import com.vitaliif.library.db.entity.User;
import com.vitaliif.library.db.repo.BookRepository;
import com.vitaliif.library.db.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookConverter bookConverter;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetAllAvailableBooks() {
        List<Book> mockBooks = Arrays.asList(createAvailableBook(), createUnavailableBook());
        when(bookRepository.findAll()).thenReturn(mockBooks);

        when(bookConverter.covert(mockBooks.get(0))).thenReturn(createBookDto(mockBooks.get(0)));

        List<BookDto> result = bookService.getAllAvailableBooks();
        assertEquals(1, result.size());
        assertEquals("Book1", result.get(0).getTitle());
    }

    @Test
    public void testGetBorrowedBooks() {
        Optional<User> mockUser = Optional.of(createUserWithBorrowedBooks());
        when(userRepository.findById(1)).thenReturn(mockUser);

        List<Book> mockBooks = Arrays.asList(createBook(), createBook());

        when(bookConverter.covert(mockBooks.get(0))).thenReturn(createBookDto(mockBooks.get(0)));

        List<BookDto> result = bookService.getBorrowedBooks(1, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(2, result.size());
        assertEquals("Book1", result.get(0).getTitle());
        assertEquals("Book1", result.get(1).getTitle());
    }

    private Book createAvailableBook() {
        Book book = new Book();
        book.setName("Book1");
        book.setBorrowedList(Collections.emptyList());
        return book;
    }

    private Book createUnavailableBook() {
        Book book = new Book();
        book.setName("Book2");
        Borrowed borrowed = new Borrowed();
        borrowed.setEndAt(LocalDate.now().plusDays(1));
        book.setBorrowedList(Collections.singletonList(borrowed));
        return book;
    }

    private User createUserWithBorrowedBooks() {
        User user = new User();
        Borrowed borrowed1 = new Borrowed();
        borrowed1.setBook(createBook());
        borrowed1.setStartAt(LocalDate.now().minusDays(1));
        borrowed1.setEndAt(LocalDate.now().plusDays(1));

        Borrowed borrowed2 = new Borrowed();
        borrowed2.setBook(createBook());
        borrowed2.setStartAt(LocalDate.now().minusDays(1));
        borrowed2.setEndAt(LocalDate.now().plusDays(1));

        user.setBorrowedList(Arrays.asList(borrowed1, borrowed2));
        return user;
    }

    private Book createBook() {
        Book book = new Book();
        book.setName("Book1");
        return book;
    }

    private BookDto createBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getName());
        return bookDto;
    }
}

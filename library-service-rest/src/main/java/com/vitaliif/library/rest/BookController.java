package com.vitaliif.library.rest;


import com.vitaliif.library.bl.dto.BookDto;
import com.vitaliif.library.bl.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/available")
    public List<BookDto> getAllAvailableBooks() {
        return bookService.getAllAvailableBooks();
    }

    @GetMapping("/borrowed")
    public List<BookDto> getBorrowedBooks(@RequestParam("userId") Integer userId,
                                          @RequestParam("startDate") LocalDate startDate,
                                          @RequestParam("endDate")LocalDate endDate) {
        return bookService.getBorrowedBooks(userId, startDate, endDate);
    }
}

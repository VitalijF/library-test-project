package com.vitaliif.library.rest;

import com.vitaliif.library.bl.dto.BookDto;
import com.vitaliif.library.bl.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllAvailableBooks() throws Exception {
        List<BookDto> mockBooks = Arrays.asList(new BookDto(/*...*/), new BookDto(/*...*/));
        when(bookService.getAllAvailableBooks()).thenReturn(mockBooks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockBooks.size()));
    }

    @Test
    public void testGetBorrowedBooks() throws Exception {
        List<BookDto> mockBorrowedBooks = Arrays.asList(new BookDto(), new BookDto());
        when(bookService.getBorrowedBooks(1, LocalDate.now(), LocalDate.now())).thenReturn(mockBorrowedBooks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/borrowed")
                        .param("userId", "1")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockBorrowedBooks.size()));
    }
}

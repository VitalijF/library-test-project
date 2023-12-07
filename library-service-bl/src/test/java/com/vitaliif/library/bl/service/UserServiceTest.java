package com.vitaliif.library.bl.service;

import com.vitaliif.library.bl.converter.UserConverter;
import com.vitaliif.library.bl.dto.UserDto;
import com.vitaliif.library.bl.service.UserService;
import com.vitaliif.library.db.entity.Borrowed;
import com.vitaliif.library.db.entity.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAllUsersWhoBorrowedBook() {
        List<User> mockUsers = Arrays.asList(createUserWithBorrowedBook(), createUserWithoutBorrowedBook());
        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userConverter.covert(mockUsers.get(0))).thenReturn(createUserDto());

        List<UserDto> result = userService.getAllUsersWhoBorrowedBook();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    public void testGetAllUsersWhoBorrowedBookForSpecificDate() {
        List<User> mockUsers = Arrays.asList(createUserWithBorrowedBookForSpecificDate(), createUserWithoutBorrowedBook());
        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userConverter.covert(mockUsers.get(0))).thenReturn(createUserDto());

        List<UserDto> result = userService.getAllUsersWhoBorrowedBook(LocalDate.now());
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    public void testGetAllUsersWhoNotBorrowedBook() {
        List<User> mockUsers = Arrays.asList(createUserWithoutBorrowedBook(), createUserWithReturnedBook());
        when(userRepository.findAll()).thenReturn(mockUsers);

        when(userConverter.covert(mockUsers.get(0))).thenReturn(createUserDto());

        List<UserDto> result = userService.getAllUsersWhoNotBorrowedBook();
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("John", result.get(1).getFirstName());
    }

    private User createUserWithBorrowedBook() {
        User user = new User();
        Borrowed borrowedBook = new Borrowed();
        borrowedBook.setEndAt(null);
        user.setBorrowedList(Collections.singletonList(borrowedBook));
        return user;
    }

    private User createUserWithoutBorrowedBook() {
        return new User();
    }

    private User createUserWithBorrowedBookForSpecificDate() {
        User user = new User();
        Borrowed borrowedBook = new Borrowed();
        borrowedBook.setStartAt(LocalDate.now());
        user.setBorrowedList(Collections.singletonList(borrowedBook));
        return user;
    }

    private User createUserWithReturnedBook() {
        User user = new User();
        Borrowed borrowedBook = new Borrowed();
        borrowedBook.setEndAt(LocalDate.now().minusDays(1));
        user.setBorrowedList(Collections.singletonList(borrowedBook));
        return user;
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        return userDto;
    }
}

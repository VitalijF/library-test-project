package com.vitaliif.library.rest;

import com.vitaliif.library.bl.dto.UserDto;
import com.vitaliif.library.bl.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/borrowed")
    public List<UserDto> getUsersWhoBorrowedBook() {
        return userService.getAllUsersWhoBorrowedBook();
    }


    @GetMapping("/borrowed/{date}")
    public List<UserDto> getUsersWhoBorrowedBookForSpecificDate(@PathVariable LocalDate date) {
        return userService.getAllUsersWhoBorrowedBook(date);
    }

    @GetMapping("/not-borrowed")
    public List<UserDto> getUsersWhoNotBorrowedBook() {
        return userService.getAllUsersWhoNotBorrowedBook();
    }
}

package com.vitaliif.library.bl.service;

import com.vitaliif.library.bl.converter.UserConverter;
import com.vitaliif.library.bl.dto.UserDto;
import com.vitaliif.library.db.entity.Borrowed;
import com.vitaliif.library.db.entity.User;
import com.vitaliif.library.db.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository,
                       UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Transactional
    public List<UserDto> getAllUsersWhoBorrowedBook() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> atLeastOneBookBorrowed(user.getBorrowedList()))
                .map(userConverter::covert)
                .toList();

    }

    @Transactional
    public List<UserDto> getAllUsersWhoBorrowedBook(LocalDate date) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> borrowedBookForSpecificDate(user.getBorrowedList(), date))
                .map(userConverter::covert)
                .toList();
    }

    @Transactional
    public List<UserDto> getAllUsersWhoNotBorrowedBook() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .filter(user -> (CollectionUtils.isEmpty(user.getBorrowedList()) || allBooksHaveBeenReturned(user.getBorrowedList())))
                .map(userConverter::covert)
                .toList();
    }

    private boolean allBooksHaveBeenReturned(List<Borrowed> borrowedList) {
        return borrowedList.stream().allMatch(b -> b.getEndAt() != null && LocalDate.now().isAfter(b.getEndAt()));
    }

    private boolean atLeastOneBookBorrowed(List<Borrowed> borrowedList) {
        if (CollectionUtils.isEmpty(borrowedList)) {
            return false;
        }
        return borrowedList.stream().anyMatch(b -> b.getEndAt() == null || !LocalDate.now().isAfter(b.getEndAt()));
    }

    private boolean borrowedBookForSpecificDate(List<Borrowed> borrowedList, LocalDate date) {
        if (CollectionUtils.isEmpty(borrowedList)) {
            return false;
        }
        return borrowedList.stream().anyMatch(b -> b.getStartAt().equals(date));
    }

}

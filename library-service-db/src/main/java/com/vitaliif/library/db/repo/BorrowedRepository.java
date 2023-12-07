package com.vitaliif.library.db.repo;

import com.vitaliif.library.db.entity.Book;
import com.vitaliif.library.db.entity.Borrowed;
import com.vitaliif.library.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowedRepository extends JpaRepository<Borrowed, Integer> {

    Optional<Borrowed> findBorrowedByBookAndUser(Book book, User user);
}

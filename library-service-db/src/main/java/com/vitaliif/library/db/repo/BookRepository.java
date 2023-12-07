package com.vitaliif.library.db.repo;

import com.vitaliif.library.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findBookByName(String name);
}

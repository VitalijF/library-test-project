package com.vitaliif.library.db.repo;

import com.vitaliif.library.db.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<Author> findAuthorByName(String name);
}

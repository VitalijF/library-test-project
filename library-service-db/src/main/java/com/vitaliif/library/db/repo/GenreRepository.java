package com.vitaliif.library.db.repo;

import com.vitaliif.library.db.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    Optional<Genre> findGenreByName(String name);
}

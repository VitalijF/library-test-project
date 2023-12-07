package com.vitaliif.library.db.repo;

import com.vitaliif.library.db.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

    Optional<Publisher> findPublisherByName(String name);
}

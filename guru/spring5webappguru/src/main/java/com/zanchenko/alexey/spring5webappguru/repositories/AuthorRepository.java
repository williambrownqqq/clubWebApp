package com.zanchenko.alexey.spring5webappguru.repositories;

import com.zanchenko.alexey.spring5webappguru.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}

package com.zanchenko.alexey.spring5webappguru.repositories;

import com.zanchenko.alexey.spring5webappguru.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}

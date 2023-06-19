package com.zanchenko.alexey.spring5webappguru.repositories;

import com.zanchenko.alexey.spring5webappguru.model.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}

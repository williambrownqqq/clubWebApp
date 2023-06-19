package com.masterswork.mail.repository;

import com.masterswork.mail.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    boolean existsByName(String name);

    Optional<Template> findByName(String name);

}

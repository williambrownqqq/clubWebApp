package com.masterswork.mail.repository;

import com.masterswork.mail.model.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Page<Email> findAllByCreatedBy(String username, Pageable pageable);
}

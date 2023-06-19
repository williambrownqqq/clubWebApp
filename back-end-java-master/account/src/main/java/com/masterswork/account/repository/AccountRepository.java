package com.masterswork.account.repository;

import com.masterswork.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findFirstByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<Account> findAllByUserIdIn(Collection<Long> user_id);
}

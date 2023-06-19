package com.masterswork.account.repository;

import com.masterswork.account.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Page<Role> findAllByAccounts_Id(Long accountId, Pageable pageable);

    Page<Role> findAllByAccounts_Username(String username, Pageable pageable);

    Role findByName(String name);
}

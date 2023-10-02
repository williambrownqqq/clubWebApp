package com.alex.zanchenko.web.repository;

import com.alex.zanchenko.web.model.Role;
import com.alex.zanchenko.web.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

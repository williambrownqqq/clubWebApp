package com.alex.zanchenko.web.service;

import com.alex.zanchenko.web.dto.RegistrationDTO;
import com.alex.zanchenko.web.model.UserEntity;

public interface UserService {
    void saveUser(RegistrationDTO registrationDTO);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}

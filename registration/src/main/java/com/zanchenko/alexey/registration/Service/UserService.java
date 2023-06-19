package com.zanchenko.alexey.registration.Service;

import com.zanchenko.alexey.registration.DTO.UserDTO;
import com.zanchenko.alexey.registration.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService{

    String save(UserDTO userDTO);
}

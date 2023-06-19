package com.zanchenko.alexey.registration.Service.ServiceIMPL;

import com.zanchenko.alexey.registration.DTO.UserDTO;
import com.zanchenko.alexey.registration.Entity.User;
import com.zanchenko.alexey.registration.Repository.UserRepository;
import com.zanchenko.alexey.registration.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public String save(UserDTO userDTO) {

        User user = new User(
                userDTO.getUser_id(),
                userDTO.getName(),
                userDTO.getSurname(),
                userDTO.getEmail(),
                userDTO.getMobile_number()
        );

        userRepository.save(user);

        return user.getName();
    }
}

package com.masterswork.account.service.mapper;

import com.masterswork.account.service.mapper.qualifier.PasswordEncoded;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperUtils {

    private final PasswordEncoder passwordEncoder;

    @PasswordEncoded
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}

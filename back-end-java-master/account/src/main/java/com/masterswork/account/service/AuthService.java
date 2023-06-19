package com.masterswork.account.service;

import com.masterswork.account.api.auth.dto.AuthorizationResponseDTO;
import com.masterswork.account.api.auth.dto.SignUpRequestDTO;


public interface AuthService {

    AuthorizationResponseDTO authenticateAndGenerateTokens(String username, String password);

    AuthorizationResponseDTO refreshAccessToken(String refreshToken);

    AuthorizationResponseDTO createUser(SignUpRequestDTO signUpRequestDTO);
}

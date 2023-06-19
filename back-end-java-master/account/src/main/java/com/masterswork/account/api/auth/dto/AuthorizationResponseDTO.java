package com.masterswork.account.api.auth.dto;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class AuthorizationResponseDTO {

    private AccountResponseDTO account;

    private TokensResponseDTO tokens;
}

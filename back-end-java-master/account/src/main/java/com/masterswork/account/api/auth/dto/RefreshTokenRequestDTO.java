package com.masterswork.account.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RefreshTokenRequestDTO {

    @NotBlank
    @JsonProperty("refresh_token")
    private String refreshToken;
}

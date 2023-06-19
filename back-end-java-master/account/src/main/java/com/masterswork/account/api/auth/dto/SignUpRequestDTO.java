package com.masterswork.account.api.auth.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.masterswork.account.api.converter.StringToPersonTypeEnumConverter;
import com.masterswork.account.model.enumeration.PersonType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SignUpRequestDTO {

    @Pattern(
            regexp = "^[a-z](?:[a-z\\d]|_(?=[a-z\\d])){3,38}$",
            message = "Username must contain between 4 and 39 characters," +
                    " allowed characters: lowercase letters, numbers, underscore" +
                    " must start with lowercase letter" +
                    " can't contain two underscores in a row" +
                    " can't end with an underscore"
    )
    private String username;

    @Email
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])[\\x00-\\xFF]{6,}$",
            message = "Password must contain " +
                    " at least six ASCII characters," +
                    " at least one number," +
                    " at least one lowercase letter," +
                    " at least one uppercase letter," +
                    " at least one special character"
    )
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String fathersName;

    @NotNull
    @JsonDeserialize(converter = StringToPersonTypeEnumConverter.class)
    private PersonType type;
}

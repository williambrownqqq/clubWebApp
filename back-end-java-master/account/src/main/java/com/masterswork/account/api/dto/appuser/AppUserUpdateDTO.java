package com.masterswork.account.api.dto.appuser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.masterswork.account.api.converter.StringToPersonTypeEnumConverter;
import com.masterswork.account.model.enumeration.PersonType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AppUserUpdateDTO {

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
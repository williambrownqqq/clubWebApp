package com.masterswork.account.api.dto.account;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountUpdateDTO {

    @NotNull
    private Boolean active;
}

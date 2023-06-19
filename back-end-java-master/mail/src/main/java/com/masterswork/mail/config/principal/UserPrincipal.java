package com.masterswork.mail.config.principal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class UserPrincipal implements Principal {

    private Long accountId;

    private Long appUserId;

    private String name;

}

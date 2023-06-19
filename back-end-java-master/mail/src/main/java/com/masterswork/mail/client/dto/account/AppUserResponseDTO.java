package com.masterswork.mail.client.dto.account;

import com.masterswork.mail.client.dto.account.enumeration.PersonType;
import lombok.Data;

@Data
public class AppUserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String fathersName;

    private String email;

    private PersonType type;

    private String accountRef;
}

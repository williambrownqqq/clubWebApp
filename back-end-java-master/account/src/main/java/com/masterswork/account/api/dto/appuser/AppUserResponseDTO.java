package com.masterswork.account.api.dto.appuser;

import com.masterswork.account.model.enumeration.PersonType;
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

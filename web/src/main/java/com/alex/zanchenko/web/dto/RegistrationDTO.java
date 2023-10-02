package com.alex.zanchenko.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationDTO {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
// because registration is probably one
//of those areas where people try to fudge
//data as much as they can whenever
//somebody actually logs into any of your
//apps the register and these the actual
//register is going to be probably the
//biggest area where people will fill out
//bogus information so if you want any if
//there's anywhere where you want to put
//really rigid validation it's going to be
//in the registration dto because people
//fill out registration forms and all
//types of crazy ways so at the very least
//you want to make sure that people are
//not submit submitting Focus data so
//we'll just put a couple not empty
//validations right here so that uh we can
//be make sure that people are not
//submitting blank data to our actual
//registration form okay so let's go back

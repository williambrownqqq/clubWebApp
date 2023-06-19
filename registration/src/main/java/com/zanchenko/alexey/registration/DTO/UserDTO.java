package com.zanchenko.alexey.registration.DTO;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@Table(name = "reminder")
public class UserDTO {

    Long user_id;
    String name;
    String surname;
    String email;
    String mobile_number;
}

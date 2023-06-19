package com.masterswork.mail.api.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponseDTO {

    private Long id;
    private String subject;
    private Instant sentAt;
    private String sentBy;
    private String sentTo;
}

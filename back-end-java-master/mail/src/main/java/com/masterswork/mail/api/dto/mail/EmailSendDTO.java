package com.masterswork.mail.api.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendDTO {

    @Min(1)
    private Long toId;
    @NotBlank
    private String subject;
    @NotBlank
    private String body;
    private List<Long> attachmentsIds;
}

package com.masterswork.mail.api.dto.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TemplateWithBodyResponseDTO {

    private Long id;
    private String name;
    private String subject;
    private String body;
}

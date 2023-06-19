package com.masterswork.mail.api.dto.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailBodyGenerateFromTemplateDTO {

    private Long templateId;
    private Long toId;
    private Map<String, Object> parameters;
}

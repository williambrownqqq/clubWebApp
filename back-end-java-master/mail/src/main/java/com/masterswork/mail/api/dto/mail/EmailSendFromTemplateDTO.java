package com.masterswork.mail.api.dto.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendFromTemplateDTO {

    @Min(1)
    private Long toId;
    private Map<String, Object> parameters;
    private List<Long> attachmentsIds;
}

package com.masterswork.mail.jms.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MailSendMessage {

    @Min(1)
    private Long toId;
    @Min(1)
    private Long fromId;
    @NotBlank
    private String fromUsername;
    @Min(1)
    private Long templateId;
    private Map<String, Object> parameters;
    private List<Long> attachmentsIds;
}
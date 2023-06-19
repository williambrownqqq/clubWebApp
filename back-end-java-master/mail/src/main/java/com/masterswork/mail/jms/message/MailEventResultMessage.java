package com.masterswork.mail.jms.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MailEventResultMessage implements Serializable {

    private String status;
    private Long processInstanceId;
    private Long stageId;
}
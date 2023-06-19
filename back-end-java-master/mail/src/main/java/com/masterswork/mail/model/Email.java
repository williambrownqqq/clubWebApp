package com.masterswork.mail.model;

import com.masterswork.mail.model.base.AuditedEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email")
public class Email extends AuditedEntity {

    @Id
    @Column(name = "email_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Column(name = "sent_by")
    private String sentBy;

    @Column(name = "sent_to")
    private String sentTo;

    public Email(String subject, Instant sentAt, String sentBy, String sentTo) {
        this.subject = subject;
        this.sentAt = sentAt;
        this.sentBy = sentBy;
        this.sentTo = sentTo;
    }
}

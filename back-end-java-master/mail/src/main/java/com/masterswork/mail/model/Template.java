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
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "template")
public class Template extends AuditedEntity {

    @Id
    @Column(name = "template_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "subject")
    private String subject;

    @Lob
    @Column(name = "body", columnDefinition = "TEXT NOT NULL")
    private String body;

    public String getThymleafTemplateName() {
        return "db-" + name;
    }
}

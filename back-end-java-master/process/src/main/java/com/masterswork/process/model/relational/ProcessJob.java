package com.masterswork.process.model.relational;

import com.masterswork.process.api.dto.process.AdditionalStageData;
import com.masterswork.process.model.relational.base.AuditedEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "process_job")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProcessJob extends AuditedEntity {

    @Id
    @Column(name = "process_job_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "schema_id")
    private String schemaId;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "executed_on")
    private Instant executedOn;

    @Column(name = "is_executed", columnDefinition = "boolean default false")
    private boolean isExecuted = false;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "owner_username")
    private String ownerUsername;

    @Type(type = "jsonb")
    @NotEmpty
    @Column(columnDefinition = "jsonb", name = "participants_ids")
    private Set<Long> participantsIds;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "stage_data")
    private Map<Long, Map<String, Object>> stageData;

    public void markAsExecuted() {
        this.isExecuted = Boolean.TRUE;
        this.executedOn = Instant.now();
    }
}

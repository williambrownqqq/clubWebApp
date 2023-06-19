package com.masterswork.process.model.relational;

import com.masterswork.process.model.relational.base.AuditedEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document_review_result")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class DocumentReviewResult extends AuditedEntity {

    @Id
    @Column(name = "document_review_result_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "properties")
    private Map<String, Object> properties;

    @ManyToOne
    @JoinColumn(name = "document_review_id", nullable = false)
    private DocumentReview documentReview;

}

package com.masterswork.process.model.relational;

import com.masterswork.process.model.relational.base.AuditedEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document_review_comment")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class DocumentReviewComment extends AuditedEntity {

    @Id
    @Column(name = "document_review_comment_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Lob
    @Column(name = "text", columnDefinition = "TEXT NOT NULL")
    private String text;

    @ManyToOne
    @JoinColumn(name = "document_review_id", nullable = false)
    private DocumentReview documentReview;
}

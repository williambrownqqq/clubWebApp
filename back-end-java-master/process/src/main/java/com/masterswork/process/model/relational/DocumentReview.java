package com.masterswork.process.model.relational;

import com.masterswork.process.model.enumeration.DocumentReviewStatus;
import com.masterswork.process.model.relational.base.AuditedEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document_review")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class DocumentReview extends AuditedEntity {

    @Id
    @Column(name = "document_review_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "approves_required", nullable = false)
    private Long approvesRequired;

    @Column(name = "approves_received", nullable = false)
    private Long approvesReceived;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DocumentReviewStatus status;

    @Column(name = "uploader_id", nullable = false)
    private Long uploaderId;

    @Lob
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "process_instance_id", nullable = false)
    private ProcessInstance processInstance;

    @Column(name = "stage_id", nullable = false)
    private Long stageId;

    @ElementCollection
    @CollectionTable(name = "document_review_attachments", joinColumns = @JoinColumn(name="document_review_id"))
    @Column(name="attachment_id")
    private Set<Long> attachmentsIds;

    @ElementCollection
    @CollectionTable(name = "document_review_reviewers", joinColumns = @JoinColumn(name="document_review_id"))
    @Column(name="reviewer_id")
    private Set<Long> reviewersIds;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentReview")
    private Set<DocumentReviewComment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentReview")
    private Set<DocumentReviewResult> reviews;

    public boolean isCurrent() {
        return stageId.equals(processInstance.getCurrentStage());
    }

    public void addComment(Long authorId, String text) {
        DocumentReviewComment documentReviewComment = DocumentReviewComment.builder()
                .authorId(authorId)
                .text(text)
                .documentReview(this)
                .build();
        this.comments.add(documentReviewComment);
    }

    public void editComment(Long commentId, Long authorId, String text) {
        DocumentReviewComment existingComment = comments.stream()
                .filter(comment -> commentId.equals(comment.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No comment with id: " + commentId + " for review with id: " + this.id));

        if (!authorId.equals(existingComment.getAuthorId())) {
            throw new AccessDeniedException("Comment can only be edited by author");
        }
        existingComment.setText(text);
    }

    public void addReview(boolean approved, Long authorId, Map<String, Object> properties) {
        Optional<DocumentReviewResult> existingReview = reviews.stream()
                .filter(documentReviewResult -> documentReviewResult.getAuthorId().equals(authorId))
                .findFirst();

        if (existingReview.isPresent()) {
            existingReview.get()
                    .setIsApproved(approved)
                    .setProperties(properties);
        } else {
            reviews.add(
                    DocumentReviewResult.builder()
                            .isApproved(approved)
                            .authorId(authorId)
                            .properties(properties)
                            .documentReview(this)
                            .build()
            );
        }
        calculateReviewState();
    }

    private void calculateReviewState() {
        this.approvesReceived = reviews.stream()
                .filter(DocumentReviewResult::getIsApproved)
                .count();

        boolean containsRejects = reviews.stream()
                .anyMatch(documentReviewResult -> !documentReviewResult.getIsApproved());
        if (containsRejects) {
            this.status = DocumentReviewStatus.REJECTED;
        } else if (approvesReceived >= approvesRequired) {
            this.status = DocumentReviewStatus.ACCEPTED;
        } else {
            this.status = DocumentReviewStatus.REVIEW_REQUESTED;
        }
    }

}

package com.masterswork.process.repository;

import com.masterswork.process.model.enumeration.DocumentReviewStatus;
import com.masterswork.process.model.relational.DocumentReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentReviewRepository extends JpaRepository<DocumentReview, Long> {

    boolean existsByProcessInstanceIdAndStageId(Long processInstanceId, Long stageId);

    Page<DocumentReview> findAllByUploaderIdAndStatus(Long userId, DocumentReviewStatus documentReviewStatus, Pageable pageable);

    Page<DocumentReview> findAllByUploaderId(Long userId, Pageable pageable);

    Page<DocumentReview> findAllByReviewersIdsContainingAndStatus(Long userId, DocumentReviewStatus documentReviewStatus, Pageable pageable);

    Page<DocumentReview> findAllByReviewersIdsContaining(Long userId, Pageable pageable);
}

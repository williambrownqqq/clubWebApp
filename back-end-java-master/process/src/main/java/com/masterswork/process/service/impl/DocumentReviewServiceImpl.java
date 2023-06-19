package com.masterswork.process.service.impl;

import com.masterswork.process.api.dto.review.CommentReviewCreateDTO;
import com.masterswork.process.api.dto.review.DocumentReviewResponseDTO;
import com.masterswork.process.api.dto.review.ReviewMaterialsSubmitDTO;
import com.masterswork.process.api.dto.review.ReviewSubmitDTO;
import com.masterswork.process.client.StorageClient;
import com.masterswork.process.model.enumeration.DocumentReviewStatus;
import com.masterswork.process.model.relational.DocumentReview;
import com.masterswork.process.repository.DocumentReviewRepository;
import com.masterswork.process.service.DocumentReviewService;
import com.masterswork.process.service.SecurityUtils;
import com.masterswork.process.service.StageManager;
import com.masterswork.process.service.mapper.DocumentReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.Map;

import static com.masterswork.process.model.enumeration.DocumentReviewStatus.*;

@Service
@RequiredArgsConstructor
public class DocumentReviewServiceImpl implements DocumentReviewService {

    private final DocumentReviewRepository documentReviewRepository;
    private final StageManager stageManager;
    private final StorageClient storageClient;
    private final DocumentReviewMapper documentReviewMapper;


    @Override
    @Transactional
    public Page<DocumentReviewResponseDTO> getAllUploaderReviews(Long userid, DocumentReviewStatus documentReviewStatus, Pageable pageable) {
        Page<DocumentReview> reviews = documentReviewStatus == null ?
                documentReviewRepository.findAllByUploaderId(userid, pageable) :
                documentReviewRepository.findAllByUploaderIdAndStatus(userid, documentReviewStatus, pageable);
        return reviews.map(documentReviewMapper::toDto);
    }

    @Override
    @Transactional
    public Page<DocumentReviewResponseDTO> getAllReviewerReviews(Long userid, DocumentReviewStatus documentReviewStatus, Pageable pageable) {
        Page<DocumentReview> reviews = documentReviewStatus == null ?
                documentReviewRepository.findAllByReviewersIdsContaining(userid, pageable) :
                documentReviewRepository.findAllByReviewersIdsContainingAndStatus(userid, documentReviewStatus, pageable);
        return reviews.map(documentReviewMapper::toDto);
    }

    @Override
    @Transactional
    public DocumentReviewResponseDTO postReviewMaterials(Long id, ReviewMaterialsSubmitDTO reviewMaterialsSubmitDTO) {
        DocumentReview documentReview = documentReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No review with id: " + id));
        if (!documentReview.getUploaderId().equals(SecurityUtils.getCurrentAppUserId())) {
            throw new AccessDeniedException("Access denied");
        }
        if (ACCEPTED.equals(documentReview.getStatus())) {
            throw new IllegalStateException("Unable to edit approved review");
        }

        documentReview.setText(reviewMaterialsSubmitDTO.getText());
        documentReview.setAttachmentsIds(reviewMaterialsSubmitDTO.getAttachmentsIds());
        return documentReviewMapper.toDto(documentReviewRepository.save(documentReview));
    }

    @Override
    @Transactional
    public DocumentReviewResponseDTO requestReview(Long id) {
        DocumentReview documentReview = documentReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No review with id: " + id));
        if (!documentReview.getUploaderId().equals(SecurityUtils.getCurrentAppUserId())) {
            throw new AccessDeniedException("Access denied");
        }

        DocumentReviewStatus status = documentReview.getStatus();
        if (documentReview.isCurrent() && PENDING_SUBMIT.equals(status) || REJECTED.equals(status)) {
            if (documentReview.getText() == null && ObjectUtils.isEmpty(documentReview.getAttachmentsIds())) {
                throw new IllegalStateException("No information to review");
            } else {
                storageClient.grantFilesAccessByUsersIds(
                        documentReview.getAttachmentsIds(),
                        documentReview.getReviewersIds(),
                        Map.of("permissionType", "READ")
                );
                documentReview.setStatus(REVIEW_REQUESTED);
                return documentReviewMapper.toDto(documentReviewRepository.save(documentReview));
            }
        } else {
            throw new IllegalStateException(
                    "Review can only be requested to current process stages with PENDING_SUBMIT or REJECTED statuses");
        }
    }

    @Override
    @Transactional
    public DocumentReviewResponseDTO postComment(Long id, CommentReviewCreateDTO commentReviewCreateDTO) {
        DocumentReview documentReview = documentReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No review with id: " + id));
        Long currentAppUserId = SecurityUtils.getCurrentAppUserId();
        if (!documentReview.getReviewersIds().contains(currentAppUserId)
                && !documentReview.getUploaderId().equals(currentAppUserId)) {
            throw new AccessDeniedException("Only uploader or reviewer can add comments");
        }

        documentReview.addComment(currentAppUserId, commentReviewCreateDTO.getText());
        return documentReviewMapper.toDto(documentReviewRepository.save(documentReview));
    }

    @Override
    @Transactional
    public DocumentReviewResponseDTO updateComment(Long processId, Long commentId, CommentReviewCreateDTO commentReviewCreateDTO) {
        DocumentReview documentReview = documentReviewRepository.findById(processId)
                .orElseThrow(() -> new EntityNotFoundException("No review with id: " + processId));
        Long currentAppUserId = SecurityUtils.getCurrentAppUserId();
        if (!documentReview.getReviewersIds().contains(currentAppUserId)
                && !documentReview.getUploaderId().equals(currentAppUserId)) {
            throw new AccessDeniedException("Only uploader or reviewer can edit comments");
        }

        documentReview.editComment(commentId, currentAppUserId, commentReviewCreateDTO.getText());
        return documentReviewMapper.toDto(documentReviewRepository.save(documentReview));
    }

    @Override
    @Transactional
    public DocumentReviewResponseDTO postReview(Long id, ReviewSubmitDTO reviewSubmitDTO) {
        DocumentReview documentReview = documentReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No review with id: " + id));
        Long currentAppUserId = SecurityUtils.getCurrentAppUserId();
        if (!documentReview.getReviewersIds().contains(currentAppUserId)) {
            throw new AccessDeniedException("Access denied");
        }

        DocumentReviewStatus currentStatus = documentReview.getStatus();
        if (documentReview.isCurrent() && REVIEW_REQUESTED.equals(currentStatus) || REJECTED.equals(currentStatus)) {
            documentReview.addReview(reviewSubmitDTO.getApprove(), currentAppUserId, reviewSubmitDTO.getProperties());
            DocumentReviewStatus newStatus = documentReview.getStatus();
            if (!currentStatus.equals(newStatus) && newStatus.isFinal()) {
                stageManager.runNextStage(documentReview.getProcessInstance().getId(), newStatus.name());
            }
        } else {
            throw new IllegalStateException(
                "Can only review current process stages with REVIEW_REQUESTED or REJECTED statuses");
        }

        return documentReviewMapper.toDto(documentReviewRepository.save(documentReview));
    }

}

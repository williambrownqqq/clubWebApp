package com.masterswork.process.service;

import com.masterswork.process.api.dto.review.CommentReviewCreateDTO;
import com.masterswork.process.api.dto.review.DocumentReviewResponseDTO;
import com.masterswork.process.api.dto.review.ReviewMaterialsSubmitDTO;
import com.masterswork.process.api.dto.review.ReviewSubmitDTO;
import com.masterswork.process.model.enumeration.DocumentReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;


public interface DocumentReviewService {

    DocumentReviewResponseDTO postReviewMaterials(Long id, ReviewMaterialsSubmitDTO reviewMaterialsSubmitDTO);

    DocumentReviewResponseDTO requestReview(Long id);

    DocumentReviewResponseDTO postReview(Long id, ReviewSubmitDTO reviewSubmitDTO);

    DocumentReviewResponseDTO postComment(Long id, CommentReviewCreateDTO commentReviewCreateDTO);

    DocumentReviewResponseDTO updateComment(Long processId, Long commentId, CommentReviewCreateDTO commentReviewCreateDTO);

    Page<DocumentReviewResponseDTO> getAllUploaderReviews(Long userid, DocumentReviewStatus documentReviewStatus, Pageable pageable);

    Page<DocumentReviewResponseDTO> getAllReviewerReviews(Long userid, DocumentReviewStatus documentReviewStatus, Pageable pageable);
}

package com.masterswork.process.api;

import com.masterswork.process.api.dto.review.CommentReviewCreateDTO;
import com.masterswork.process.api.dto.review.DocumentReviewResponseDTO;
import com.masterswork.process.api.dto.review.ReviewMaterialsSubmitDTO;
import com.masterswork.process.api.dto.review.ReviewSubmitDTO;
import com.masterswork.process.config.principal.UserPrincipal;
import com.masterswork.process.model.enumeration.DocumentReviewStatus;
import com.masterswork.process.repository.DocumentReviewRepository;
import com.masterswork.process.service.DocumentReviewService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class DocumentReviewController {

    private final DocumentReviewService documentReviewService;
    private final DocumentReviewRepository documentReviewRepository;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/current/upload")
    public ResponseEntity<Page<DocumentReviewResponseDTO>> getReviewsForCurrentUserUploader(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) DocumentReviewStatus documentReviewStatus,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(documentReviewService.getAllUploaderReviews(userPrincipal.getAppUserId(), documentReviewStatus, pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/current/reviewer")
    public ResponseEntity<Page<DocumentReviewResponseDTO>> getReviewsForCurrentUserReviewer(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) DocumentReviewStatus documentReviewStatus,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(documentReviewService.getAllReviewerReviews(userPrincipal.getAppUserId(), documentReviewStatus, pageable));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/material")
    public ResponseEntity<DocumentReviewResponseDTO> postReviewMaterials(
            @PathVariable Long id, @RequestBody ReviewMaterialsSubmitDTO reviewMaterialsSubmitDTO) {
        return ResponseEntity.ok(documentReviewService.postReviewMaterials(id, reviewMaterialsSubmitDTO));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/request")
    public ResponseEntity<DocumentReviewResponseDTO> requestReview(@PathVariable Long id) {
        return ResponseEntity.ok(documentReviewService.requestReview(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/submit")
    public ResponseEntity<DocumentReviewResponseDTO> submitReview(
            @PathVariable Long id, @Valid @RequestBody ReviewSubmitDTO reviewSubmitDTO) {
        return ResponseEntity.ok(documentReviewService.postReview(id, reviewSubmitDTO));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/comment")
    public ResponseEntity<DocumentReviewResponseDTO> addComment(
            @PathVariable Long id, @Valid @RequestBody CommentReviewCreateDTO commentReviewCreateDTO) {
        return ResponseEntity.ok(documentReviewService.postComment(id, commentReviewCreateDTO));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{reviewId}/comment/{commentId}")
    public ResponseEntity<DocumentReviewResponseDTO> editComment(
            @PathVariable Long reviewId ,@PathVariable Long commentId, @Valid @RequestBody CommentReviewCreateDTO commentReviewCreateDTO) {
        return ResponseEntity.ok(documentReviewService.updateComment(reviewId, commentId, commentReviewCreateDTO));
    }

}

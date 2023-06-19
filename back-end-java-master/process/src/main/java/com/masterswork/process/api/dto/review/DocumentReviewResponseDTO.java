package com.masterswork.process.api.dto.review;

import com.masterswork.process.model.enumeration.DocumentReviewStatus;
import lombok.Data;

import java.util.Set;

@Data
public class DocumentReviewResponseDTO {

    private Long id;

    private Long approvesRequired;

    private Long approvesReceived;

    private DocumentReviewStatus status;

    private Long uploaderId;

    private String text;

    private String processInstanceRef;

    private Long stageId;

    private Set<Long> attachmentsIds;

    private Set<Long> reviewersIds;

    private Set<CommentResponseDTO> comments;

    private Set<DocumentReviewResultResponseDTO> reviews;
}

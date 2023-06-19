package com.masterswork.process.api.dto.review;

import lombok.Data;

@Data
public class CommentResponseDTO {

    private Long id;
    private Long authorId;
    private String text;
    private String documentReviewRef;
}

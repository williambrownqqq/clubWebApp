package com.masterswork.process.api.dto.review;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentReviewCreateDTO {
    @NotBlank
    private String text;
}

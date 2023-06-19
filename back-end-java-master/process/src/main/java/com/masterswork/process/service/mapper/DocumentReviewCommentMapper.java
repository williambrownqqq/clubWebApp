package com.masterswork.process.service.mapper;

import com.masterswork.process.api.dto.review.CommentResponseDTO;
import com.masterswork.process.api.dto.review.DocumentReviewResponseDTO;
import com.masterswork.process.model.relational.DocumentReview;
import com.masterswork.process.model.relational.DocumentReviewComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface DocumentReviewCommentMapper {

    @Mapping(target = "documentReviewRef", expression = "java(mapDocumentReviewRef(comment))")
    CommentResponseDTO toDto(DocumentReviewComment comment);

    default String mapDocumentReviewRef(DocumentReviewComment comment) {
        return Optional.ofNullable(comment.getDocumentReview())
                .map(review -> "/review/" + review.getId())
                .orElse(null);
    }

}

package com.masterswork.process.service.mapper;

import com.masterswork.process.api.dto.review.DocumentReviewResultResponseDTO;
import com.masterswork.process.model.relational.DocumentReviewResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface DocumentReviewResultMapper {

    @Mapping(target = "documentReviewRef", expression = "java(mapDocumentReviewRef(documentReviewResult))")
    DocumentReviewResultResponseDTO toDto(DocumentReviewResult documentReviewResult);

    default String mapDocumentReviewRef(DocumentReviewResult documentReviewResult) {
        return Optional.ofNullable(documentReviewResult.getDocumentReview())
                .map(review -> "/review/" + review.getId())
                .orElse(null);
    }

}

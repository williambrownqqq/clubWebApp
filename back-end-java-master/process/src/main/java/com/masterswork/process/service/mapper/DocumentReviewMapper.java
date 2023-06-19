package com.masterswork.process.service.mapper;

import com.masterswork.process.api.dto.review.DocumentReviewResponseDTO;
import com.masterswork.process.model.relational.DocumentReview;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {DocumentReviewCommentMapper.class, DocumentReviewResultMapper.class})
public interface DocumentReviewMapper {

    @Mapping(target = "processInstanceRef", expression = "java(mapProcessInstanceRef(documentReview))")
    DocumentReviewResponseDTO toDto(DocumentReview documentReview);

    default String mapProcessInstanceRef(DocumentReview documentReview) {
        return Optional.ofNullable(documentReview.getProcessInstance())
                .map(processInstance -> "/process/" + processInstance.getId())
                .orElse(null);
    }

}

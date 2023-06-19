package com.masterswork.process.api.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
public class DocumentReviewResultResponseDTO {

    private Long authorId;
    private Boolean isApproved;
    private Map<String, Object> properties;
    private String documentReviewRef;
}

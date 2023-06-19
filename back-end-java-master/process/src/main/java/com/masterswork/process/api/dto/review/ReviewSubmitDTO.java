package com.masterswork.process.api.dto.review;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class ReviewSubmitDTO {
    @NotNull
    private Boolean approve;
    private Map<String, Object> properties;
}

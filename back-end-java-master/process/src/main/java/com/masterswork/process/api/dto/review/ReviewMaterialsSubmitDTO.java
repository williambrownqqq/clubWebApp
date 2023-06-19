package com.masterswork.process.api.dto.review;

import lombok.Data;

import java.util.Set;

@Data
public class ReviewMaterialsSubmitDTO {

    private String text;
    private Set<Long> attachmentsIds;
}

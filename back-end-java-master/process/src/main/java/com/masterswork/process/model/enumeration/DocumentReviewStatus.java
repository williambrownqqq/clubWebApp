package com.masterswork.process.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentReviewStatus {
    PENDING_SUBMIT(false),
    REVIEW_REQUESTED(false),
    ACCEPTED(true),
    REJECTED(true);

    private final boolean isFinal;
}

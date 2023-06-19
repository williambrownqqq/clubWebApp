package com.masterswork.process.api.dto.process;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProcessResponseDTO {

    private Long id;
    private String schemaId;
    private Long currentStage;
    private boolean isFinished;
    private Long ownerId;
    private String ownerUsername;
    private Long subordinateId;
}

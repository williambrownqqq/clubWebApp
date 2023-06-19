package com.masterswork.process.api.dto.process;

import lombok.Data;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Data
public class ProcessStartRequest {

    private Instant startTime;
    private String  schemaId;
    private Set<Long> participantsIds;
    private Set<Long> organizationUnitsIds;
    private Map<Long, Map<String, Object>> stageData;
}

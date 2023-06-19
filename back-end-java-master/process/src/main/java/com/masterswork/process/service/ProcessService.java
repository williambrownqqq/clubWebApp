package com.masterswork.process.service;

import com.masterswork.process.api.dto.process.ProcessResponseDTO;
import com.masterswork.process.api.dto.process.ProcessStartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessService {

    void startOrScheduleRequest(ProcessStartRequest processStartRequest);

    void startScheduledProcesses();

    Page<ProcessResponseDTO> getProcessInstancesForUser(Long appUserId, Pageable pageable);

    ProcessResponseDTO getProcessInstanceById(Long instanceId);

    Page<ProcessResponseDTO> getAllProcesses(Pageable pageable);
}

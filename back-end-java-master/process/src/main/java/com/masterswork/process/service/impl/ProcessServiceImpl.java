package com.masterswork.process.service.impl;

import com.masterswork.process.api.dto.process.ProcessResponseDTO;
import com.masterswork.process.api.dto.process.ProcessStartRequest;
import com.masterswork.process.client.OrganizationClient;
import com.masterswork.process.model.relational.ProcessInstance;
import com.masterswork.process.model.relational.ProcessJob;
import com.masterswork.process.repository.ProcessInstanceRepository;
import com.masterswork.process.repository.ProcessJobRepository;
import com.masterswork.process.service.ProcessService;
import com.masterswork.process.service.SecurityUtils;
import com.masterswork.process.service.StageManager;
import com.masterswork.process.service.mapper.ProcessMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ProcessJobRepository processJobRepository;
    private final ProcessInstanceRepository processInstanceRepository;
    private final OrganizationClient organizationClient;
    private final ProcessMapper processMapper;
    private final StageManager stageManager;

    @Override
    @Transactional
    public void  startOrScheduleRequest(ProcessStartRequest processStartRequest) {
        Long currentAppUserId = SecurityUtils.getCurrentAppUserId();
        String currentUserUsername = SecurityUtils.getCurrentUserUsername();
        Set<Long> participantsIds = getParticipantsFromDTO(processStartRequest);

        if (processStartRequest.getStartTime() != null) {
            ProcessJob processJob = processMapper.from(processStartRequest);
            processJob.setOwnerId(currentAppUserId);
            processJob.setOwnerUsername(currentUserUsername);
            processJob.setParticipantsIds(participantsIds);

            processJobRepository.save(processJob);
        } else {
            stageManager.startProcess(processStartRequest.getSchemaId(), currentAppUserId, currentUserUsername, participantsIds, processStartRequest.getStageData());
        }
    }

    @Override
    public Page<ProcessResponseDTO> getProcessInstancesForUser(Long appUserId, Pageable pageable) {
        return processInstanceRepository.findAllByOwnerIdOrSubordinateId(appUserId, appUserId, pageable)
                .map(processMapper::toDto);
    }

    @Override
    public ProcessResponseDTO getProcessInstanceById(Long instanceId) {
        ProcessInstance processInstance = processInstanceRepository.findById(instanceId)
                .orElseThrow(() -> new EntityNotFoundException("No process Instance with id " + instanceId));
        Long currentAppUserId = SecurityUtils.getCurrentAppUserId();
        if (!currentAppUserId.equals(processInstance.getOwnerId())
                && !currentAppUserId.equals(processInstance.getSubordinateId())) {
            throw new AccessDeniedException("No access to this process instance");
        }
        return processMapper.toDto(processInstance);
    }

    @Override
    public Page<ProcessResponseDTO> getAllProcesses(Pageable pageable) {
        return processInstanceRepository.findAll(pageable).map(processMapper::toDto);
    }

    @Override
    public void startScheduledProcesses() {
        List<ProcessJob> notExecuted = processJobRepository.findAllByIsExecutedAndStartTimeIsLessThanEqual(Boolean.FALSE, Instant.now());
        notExecuted.forEach(job -> {
            stageManager.startProcess(job.getSchemaId(), job.getOwnerId(), job.getOwnerUsername(), job.getParticipantsIds(), job.getStageData());
            job.markAsExecuted();
            processJobRepository.save(job);
        });
    }

    private Set<Long> getParticipantsFromDTO(ProcessStartRequest processStartRequest) {
        Set<Long> participantsIds = Optional.ofNullable(processStartRequest.getParticipantsIds()).orElseGet(HashSet::new);
        Set<Long> organizationUnitsIds = processStartRequest.getOrganizationUnitsIds();
        if (organizationUnitsIds != null && !organizationUnitsIds.isEmpty()) {
            participantsIds.addAll(organizationClient.getParticipantsForOrganizations(organizationUnitsIds));
        }

        return participantsIds;
    }

}

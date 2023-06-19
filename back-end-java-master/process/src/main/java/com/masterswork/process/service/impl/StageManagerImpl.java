package com.masterswork.process.service.impl;

import com.masterswork.process.api.dto.schema.stage.StageDTO;
import com.masterswork.process.model.enumeration.StageType;
import com.masterswork.process.model.relational.ProcessInstance;
import com.masterswork.process.repository.ProcessInstanceRepository;
import com.masterswork.process.service.SchemaService;
import com.masterswork.process.service.StageManager;
import com.masterswork.process.service.StageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StageManagerImpl implements StageManager {

    private final Map<StageType, StageProcessor> stageProcessors;
    private final SchemaService schemaService;
    private final ProcessInstanceRepository processInstanceRepository;

    @Override
    public void startProcess(String schemaId, Long ownerId, String ownerUsername, Set<Long> participantsIds, Map<Long, Map<String, Object>> stageData) {
        List<ProcessInstance> processInstances = participantsIds.stream()
                .map(participantId -> ProcessInstance.of(schemaId, 0L, ownerId, ownerUsername, participantId, stageData))
                .collect(Collectors.toList());
        processInstanceRepository.saveAll(processInstances);
        processInstances.forEach(this::runStage);
    }

    @Override
    @Transactional
    public void runNextStage(Long processInstanceId, String previousResult) {
        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId)
                .orElseThrow(() -> new EntityNotFoundException("No process instance with id: "+ processInstanceId));
        Long nextStageId = schemaService.getNextStageId(processInstance.getSchemaId(), processInstance.getCurrentStage(), previousResult);
        processInstance.setCurrentStage(nextStageId);

        if (nextStageId == null) {
            processInstance.setFinished(true);
            processInstanceRepository.save(processInstance);
        } else {
            processInstanceRepository.save(processInstance);
            runStage(processInstance);
        }
    }

    @Override
    public void runStage(ProcessInstance processInstance) {
        StageDTO currentStage = schemaService.getSchemaStage(processInstance.getSchemaId(), processInstance.getCurrentStage());
        StageProcessor stageProcessor = stageProcessors.get(currentStage.getStageType());
        stageProcessor.process(currentStage, processInstance);
    }
}

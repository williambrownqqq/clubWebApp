package com.masterswork.process.service.impl.processor;

import com.masterswork.process.api.dto.schema.stage.StageDTO;
import com.masterswork.process.client.OrganizationClient;
import com.masterswork.process.jwt.JwtUtil;
import com.masterswork.process.model.enumeration.DocumentReviewStatus;
import com.masterswork.process.model.relational.DocumentReview;
import com.masterswork.process.model.relational.ProcessInstance;
import com.masterswork.process.repository.DocumentReviewRepository;
import com.masterswork.process.repository.ProcessInstanceRepository;
import com.masterswork.process.repository.ProcessJobRepository;
import com.masterswork.process.service.SecurityUtils;
import com.masterswork.process.service.StageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentReviewProcessorImpl implements StageProcessor {

    private final DocumentReviewRepository documentReviewRepository;
    private final OrganizationClient organizationClient;
    private final JwtUtil jwtUtil;

    @Override
    public void process(StageDTO stageDTO, ProcessInstance processInstance) {
        Long processId = processInstance.getId();
        Long stageId = processInstance.getCurrentStage();
        if (!documentReviewRepository.existsByProcessInstanceIdAndStageId(processId, stageId)) {
            DocumentReview documentReview = DocumentReview.builder()
                    .approvesRequired(Long.valueOf((Integer) stageDTO.getProperties().get("approvesRequired")))
                    .approvesReceived(0L)
                    .status(DocumentReviewStatus.PENDING_SUBMIT)
                    .uploaderId(getIdByType(processInstance, (String) stageDTO.getProperties().get("uploader")))
                    .stageId(stageId)
                    .reviewersIds(getReviewersForDocumentReview(stageDTO))
                    .build();
            processInstance.addReview(documentReview);
        }
    }

    private Long getIdByType(ProcessInstance processInstance, String type) {
        if ("subordinate".equals(type)) {
            return processInstance.getSubordinateId();
        } else if ("owner".equals(type)) {
            return processInstance.getOwnerId();
        }

        try {
            return Long.valueOf(type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported person type");
        }
    }

    private Set<Long> getReviewersForDocumentReview(StageDTO stageDTO) {
        if ("system".equals(SecurityUtils.getCurrentUserUsername())) {
            jwtUtil.setUserAsAdmin("system", null);
        }

        Set<Long> responsibleIds = Optional.ofNullable((List<Integer>) stageDTO.getProperties().get("responsibleIds"))
                .map(this::mapIntToLongSet)
                .orElseGet(HashSet::new);
        Set<Long> responsibleOrganizationUnitsIds = Optional.ofNullable((List<Integer>) stageDTO.getProperties().get("responsibleOrganizationUnitsIds"))
                .map(this::mapIntToLongSet)
                .orElseGet(HashSet::new);

        if (!responsibleOrganizationUnitsIds.isEmpty()) {
            responsibleIds.addAll(organizationClient.getParticipantsForOrganizations(responsibleOrganizationUnitsIds));
        }
        return responsibleIds;
    }

    private Set<Long> mapIntToLongSet(List<Integer> input) {
        if (input == null) return new HashSet<>();
        return input.stream().map(Integer::longValue).collect(Collectors.toSet());
    }
}

package com.masterswork.organization.service;

import com.masterswork.organization.api.dto.process.OrganizationCreateDTO;
import com.masterswork.organization.api.dto.process.OrganizationResponseDTO;
import com.masterswork.organization.api.dto.process.OrganizationUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface OrganizationUnitService {

    OrganizationResponseDTO createOrganization(OrganizationCreateDTO organizationCreateDTO);

    OrganizationResponseDTO updateOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO);

    OrganizationResponseDTO patchOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO);

    Page<OrganizationResponseDTO> getAllOrganizations(Pageable pageable);

    OrganizationResponseDTO getOrganization(Long id);

    void deleteOrganization(Long id);

    OrganizationResponseDTO setOwner(Long organizationId, Long ownerId);

    OrganizationResponseDTO removeOwner(Long organizationId);

    Set<Long> getParticipantsIdsByOrganizationId(Long id);

    Set<Long> getParticipantsForOrganizations(Set<Long> ids);

    OrganizationResponseDTO addParticipantToOrganization(Long organizationId, Long participantId);

    OrganizationResponseDTO removeParticipantFromOrganization(Long organizationId, Long participantId);
}

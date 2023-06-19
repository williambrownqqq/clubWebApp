package com.masterswork.organization.service.impl;

import com.masterswork.organization.api.dto.process.OrganizationCreateDTO;
import com.masterswork.organization.api.dto.process.OrganizationResponseDTO;
import com.masterswork.organization.api.dto.process.OrganizationUpdateDTO;
import com.masterswork.organization.model.OrganizationUnit;
import com.masterswork.organization.model.projection.OrganizationUnitParticipantsProjection;
import com.masterswork.organization.repository.OrganizationUnitRepository;
import com.masterswork.organization.service.OrganizationUnitService;
import com.masterswork.organization.service.mapper.OrganizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationUnitServiceImpl implements OrganizationUnitService {

    private final OrganizationUnitRepository organizationUnitRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public OrganizationResponseDTO createOrganization(OrganizationCreateDTO organizationCreateDTO) {
        var newOrganization = organizationMapper.createFrom(organizationCreateDTO);
        return organizationMapper.toDto(organizationUnitRepository.save(newOrganization));
    }

    @Override
    public OrganizationResponseDTO updateOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO) {
        var organizationUnit = organizationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + id));
        organizationMapper.updateFrom(organizationUnit, organizationUpdateDTO);
        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public OrganizationResponseDTO patchOrganization(Long id, OrganizationUpdateDTO organizationUpdateDTO) {
        var organizationUnit = organizationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + id));
        organizationMapper.patchFrom(organizationUnit, organizationUpdateDTO);
        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public Page<OrganizationResponseDTO> getAllOrganizations(Pageable pageable) {
        return organizationUnitRepository.findAll(pageable).map(organizationMapper::toDto);
    }

    @Override
    public OrganizationResponseDTO getOrganization(Long id) {
        return organizationUnitRepository.findById(id)
                .map(organizationMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No organization unit with id: " + id));
    }

    @Override
    public void deleteOrganization(Long id) {
        var organizationUnit = organizationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + id));
        organizationUnit.getSubs().forEach(OrganizationUnit::removeOwner);
        organizationUnitRepository.deleteById(id);
    }

    @Override
    public OrganizationResponseDTO setOwner(Long organizationId, Long ownerId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));
        var owner = organizationUnitRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + ownerId));

        organizationUnit.setOwner(owner);

        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public OrganizationResponseDTO removeOwner(Long organizationId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));

        organizationUnit.removeOwner();

        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public Set<Long> getParticipantsIdsByOrganizationId(Long id) {
        var organizationUnit = organizationUnitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + id));
        TreeSet<Long> ids = new TreeSet<>(organizationUnit.getParticipants());
        ids.add(organizationUnit.getHeadId());
        return ids;
    }

    @Override
    public Set<Long> getParticipantsForOrganizations(Set<Long> ids) {
        return organizationUnitRepository.getParticipantsByIdIn(ids).stream()
                .flatMap(unit -> unit.getAllParticipants().stream())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public OrganizationResponseDTO addParticipantToOrganization(Long organizationId, Long participantId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));
        organizationUnit.getParticipants().add(participantId);
        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }

    @Override
    public OrganizationResponseDTO removeParticipantFromOrganization(Long organizationId, Long participantId) {
        var organizationUnit = organizationUnitRepository.findById(organizationId)
                .orElseThrow(() -> new EntityNotFoundException("No Organization with id: " + organizationId));
        organizationUnit.getParticipants().remove(participantId);
        return organizationMapper.toDto(organizationUnitRepository.save(organizationUnit));
    }
}

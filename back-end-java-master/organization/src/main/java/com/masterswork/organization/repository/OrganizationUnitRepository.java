package com.masterswork.organization.repository;

import com.masterswork.organization.model.OrganizationUnit;
import com.masterswork.organization.model.projection.OrganizationUnitParticipantsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrganizationUnitRepository extends JpaRepository<OrganizationUnit, Long> {

    OrganizationUnitParticipantsProjection getParticipantsById(Long id);

    Set<OrganizationUnitParticipantsProjection> getParticipantsByIdIn(Set<Long> ids);

}

package com.masterswork.organization.service.mapper;


import com.masterswork.organization.api.dto.process.OrganizationCreateDTO;
import com.masterswork.organization.api.dto.process.OrganizationResponseDTO;
import com.masterswork.organization.api.dto.process.OrganizationUpdateDTO;
import com.masterswork.organization.model.OrganizationUnit;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    @Mapping(target = "id", ignore = true)
    OrganizationUnit createFrom(OrganizationCreateDTO source);

    @Mapping(target = "id", ignore = true)
    void updateFrom(@MappingTarget OrganizationUnit target, OrganizationUpdateDTO source);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFrom(@MappingTarget OrganizationUnit target, OrganizationUpdateDTO source);

    @Mapping(target = "participantsIds", source = "participants")
    @Mapping(target = "subsId", expression = "java(mapSubsIds(organizationUnit))")
    @Mapping(target = "ownerRef", expression = "java(mapOwnerReference(organizationUnit))")
    OrganizationResponseDTO toDto(OrganizationUnit organizationUnit);

    List<OrganizationResponseDTO> toDto(Collection<OrganizationUnit> organizationUnits);

    default String mapOwnerReference(OrganizationUnit organizationUnit) {
        return Optional.ofNullable(organizationUnit.getOwner())
                .map(owner -> "/organization/" + owner.getId())
                .orElse(null);
    }

    default Set<Long> mapSubsIds(OrganizationUnit organizationUnit) {
        return Optional.ofNullable(organizationUnit.getSubs())
                .map(organizationUnits -> organizationUnits.stream().map(OrganizationUnit::getId).collect(Collectors.toSet()))
                .orElse(null);
    }

}
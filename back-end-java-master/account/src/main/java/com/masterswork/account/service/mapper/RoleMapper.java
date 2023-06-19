package com.masterswork.account.service.mapper;

import com.masterswork.account.api.dto.role.RoleCreateDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.api.dto.role.RoleUpdateDTO;
import com.masterswork.account.model.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDTO toDto(Role faculty);

    List<RoleResponseDTO> toDto(Collection<Role> all);

    @Mapping(target = "id", ignore = true)
    Role createFrom(RoleCreateDTO facultyResponseDTO);

    @Mapping(target = "id", ignore = true)
    void updateFrom(@MappingTarget Role target, RoleUpdateDTO source);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFrom(@MappingTarget Role target, RoleUpdateDTO source);
}
package com.masterswork.account.service.mapper;

import com.masterswork.account.api.dto.faculty.FacultyCreateDTO;
import com.masterswork.account.api.dto.faculty.FacultyResponseDTO;
import com.masterswork.account.api.dto.faculty.FacultyUpdateDTO;
import com.masterswork.account.model.Faculty;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    FacultyResponseDTO toDto(Faculty faculty);

    List<FacultyResponseDTO> toDto(Collection<Faculty> all);

    @Mapping(target = "id", ignore = true)
    Faculty createFrom(FacultyCreateDTO facultyResponseDTO);

    @Mapping(target = "id", ignore = true)
    void updateFrom(@MappingTarget Faculty target, FacultyUpdateDTO source);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFrom(@MappingTarget Faculty target, FacultyUpdateDTO source);
}

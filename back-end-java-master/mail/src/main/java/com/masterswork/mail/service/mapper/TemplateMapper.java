package com.masterswork.mail.service.mapper;

import com.masterswork.mail.api.dto.template.TemplateCreateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateResponseDTO;
import com.masterswork.mail.api.dto.template.TemplateUpdateRequestDTO;
import com.masterswork.mail.api.dto.template.TemplateWithBodyResponseDTO;
import com.masterswork.mail.model.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    Template from(TemplateCreateRequestDTO templateCreateRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    void updateFrom(@MappingTarget Template target, TemplateUpdateRequestDTO source);

    TemplateResponseDTO toDTO(Template template);

    TemplateWithBodyResponseDTO toDTOWithBody(Template template);
}

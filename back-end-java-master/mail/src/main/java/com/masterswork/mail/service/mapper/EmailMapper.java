package com.masterswork.mail.service.mapper;

import com.masterswork.mail.api.dto.mail.EmailResponseDTO;
import com.masterswork.mail.model.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailResponseDTO toDTO(Email email);

}

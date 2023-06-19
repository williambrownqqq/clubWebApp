package com.masterswork.process.service.mapper;

import com.masterswork.process.api.dto.process.ProcessResponseDTO;
import com.masterswork.process.api.dto.process.ProcessStartRequest;
import com.masterswork.process.model.relational.ProcessInstance;
import com.masterswork.process.model.relational.ProcessJob;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProcessMapper {

    ProcessJob from(ProcessStartRequest body);

    ProcessResponseDTO toDto(ProcessInstance processInstance);

}
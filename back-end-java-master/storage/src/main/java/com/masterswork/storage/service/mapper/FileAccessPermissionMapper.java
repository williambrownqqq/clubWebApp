package com.masterswork.storage.service.mapper;

import com.masterswork.storage.api.dto.access.FileAccessPermissionDTO;
import com.masterswork.storage.model.FileAccessPermission;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FileAccessPermissionMapper {

    FileAccessPermissionDTO toDto(FileAccessPermission fileAccessPermission);

}
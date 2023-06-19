package com.masterswork.storage.service.mapper;

import com.masterswork.storage.api.dto.file.StoredFileDTO;
import com.masterswork.storage.model.StoredFile;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StoredFileMapper {

    StoredFileDTO toDto(StoredFile file);

    List<StoredFileDTO> toDto(Collection<StoredFile> all);
}
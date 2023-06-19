package com.masterswork.storage.service;

import com.masterswork.storage.api.dto.file.StoredFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface StorageService {

	StoredFileDTO store(MultipartFile file);

	StoredFileDTO updateFile(MultipartFile file, Long id);

	void deleteFile(Long id);

	Page<StoredFileDTO> getAllStoredFiles(Pageable pageable);

	Page<StoredFileDTO> getCurrentUserStoredFiles(Pageable pageable);

	Resource[] loadResourcesByIds(Set<Long> ids);

	Resource loadAsResource(Long id);

}

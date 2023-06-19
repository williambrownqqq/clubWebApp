package com.masterswork.storage.service.impl;

import com.masterswork.storage.api.dto.file.StoredFileDTO;
import com.masterswork.storage.config.properties.StorageProperties;
import com.masterswork.storage.model.FileAccessPermission;
import com.masterswork.storage.model.StoredFile;
import com.masterswork.storage.model.enumeration.FilePermissionType;
import com.masterswork.storage.repository.StoredFileRepository;
import com.masterswork.storage.service.SecurityUtils;
import com.masterswork.storage.service.StorageService;
import com.masterswork.storage.service.exception.StorageException;
import com.masterswork.storage.service.exception.StorageFileNotFoundException;
import com.masterswork.storage.service.mapper.StoredFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class FileSystemStorageService implements StorageService {

	private final StoredFileRepository storedFileRepository;
	private final StoredFileMapper storedFileMapper;
	private final StorageProperties storageProperties;

	private Path rootLocation;

	@PostConstruct
	public void construct() {
		this.rootLocation = Paths.get(storageProperties.getLocation());
	}

	@Override
	public StoredFileDTO store(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new StorageException("Failed to store empty multipartFile.");
		}

		String username = SecurityUtils.getCurrentUserUsername();
		String originalFilename = multipartFile.getOriginalFilename();
		Optional<StoredFile> optionalStoredFile = storedFileRepository.findByOriginalFilenameAndOwner(originalFilename, username);

		optionalStoredFile.ifPresent(existingFile -> validateUserHasAccess(existingFile, username, FilePermissionType.WRITE));
		storeFile(username, multipartFile);

		StoredFile storedFile = optionalStoredFile.map(StoredFile::incrementVersion)
				.orElseGet(() -> StoredFile.builder()
					.originalFilename(originalFilename)
					.owner(username)
					.sizeBytes(multipartFile.getSize())
					.build()
					.addPermission(FileAccessPermission.of(FilePermissionType.READ, username))
					.addPermission(FileAccessPermission.of(FilePermissionType.WRITE, username))
					.addPermission(FileAccessPermission.of(FilePermissionType.DELETE, username))
				);

		return storedFileMapper.toDto(storedFileRepository.save(storedFile));
	}

	@Override
	public StoredFileDTO updateFile(MultipartFile file, Long id) {
		StoredFile storedFile = storedFileRepository.findById(id)
				.orElseThrow(() -> new StorageFileNotFoundException("Could not read file with Id: " + id));

		if (!Objects.equals(storedFile.getOriginalFilename(), file.getOriginalFilename())) {
			String message = String.format(
					"Unable to update file by id [%s], existing file name doesn't match: [%s] ", id, storedFile.getOriginalFilename());
			throw new StorageException(message);
		}
		String username = SecurityUtils.getCurrentUserUsername();
		validateUserHasAccess(storedFile, username, FilePermissionType.WRITE);

		storeFile(storedFile.getOwner(), file);

		return storedFileMapper.toDto(storedFileRepository.save(storedFile.incrementVersion()));
	}

	@Override
	public void deleteFile(Long id) {
		StoredFile storedFile = storedFileRepository.findById(id)
				.orElseThrow(() -> new StorageFileNotFoundException("Could not read file with Id: " + id));

		String username = SecurityUtils.getCurrentUserUsername();
		validateUserHasAccess(storedFile, username, FilePermissionType.DELETE);

		removeFile(storedFile);

		storedFileRepository.delete(storedFile);
	}

	@Override
	public Page<StoredFileDTO> getAllStoredFiles(Pageable pageable) {
		return storedFileRepository.findAll(pageable)
				.map(storedFileMapper::toDto);
	}

	@Override
	public Page<StoredFileDTO> getCurrentUserStoredFiles(Pageable pageable) {
		String username = SecurityUtils.getCurrentUserUsername();
		return storedFileRepository.findAllByUserAndPermissionType(username, FilePermissionType.READ, pageable)
				.map(storedFileMapper::toDto);
	}

	@Override
	public Resource[] loadResourcesByIds(Set<Long> ids) {
		return storedFileRepository.findAllByIdIn(ids).stream()
				.map(file -> loadAsResource(file.getId()))
				.toArray(Resource[]::new);
	}

	@Override
	public Resource loadAsResource(Long id) {
		StoredFile storedFile = storedFileRepository.findById(id)
				.orElseThrow(() -> new StorageFileNotFoundException("Could not read file with Id: " + id));
		validateUserHasAccess(storedFile, SecurityUtils.getCurrentUserUsername(), FilePermissionType.READ);

		try {
			Path file = rootLocation.resolve(Path.of(storedFile.getOwner(), storedFile.getOriginalFilename()));
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file with Id: " + id);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file with Id: " + id, e);
		}
	}

	private void validateUserHasAccess(StoredFile file, String username, FilePermissionType filePermissionType) {
		if (!file.hasAccess(filePermissionType, username)) {
			String message = String.format(
					"User [%s] doesnt have [%s] permissions for file: [%s]", username, filePermissionType, file);
			throw new AccessDeniedException(message);
		}
	}

	private void storeFile(String directory, MultipartFile multipartFile) {
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Files.createDirectories(rootLocation.resolve(directory));
			Path relativePath = Path.of(directory, multipartFile.getOriginalFilename());
			Path destinationFile = this.rootLocation.resolve(relativePath)
					.normalize()
					.toAbsolutePath();
			Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new StorageException("Failed to store multipartFile.", e);
		}
	}

	private void removeFile(StoredFile storedFile) {
		try {
			Path relativePath = Path.of(storedFile.getOwner(), storedFile.getOriginalFilename());
			Path destinationFile = this.rootLocation.resolve(relativePath)
					.normalize()
					.toAbsolutePath();
			Files.delete(destinationFile);
		} catch (IOException e) {
			throw new StorageException("Unable to remove file: " + storedFile.getId(), e);
		}
	}

}

package com.masterswork.storage.service.impl;

import com.masterswork.storage.api.dto.access.FileAccessLevelDTO;
import com.masterswork.storage.api.dto.access.FileAccessPermissionDTO;
import com.masterswork.storage.client.AccountClient;
import com.masterswork.storage.model.FileAccessPermission;
import com.masterswork.storage.model.StoredFile;
import com.masterswork.storage.model.enumeration.FilePermissionType;
import com.masterswork.storage.model.enumeration.RoleName;
import com.masterswork.storage.repository.FileAccessPermissionRepository;
import com.masterswork.storage.repository.StoredFileRepository;
import com.masterswork.storage.service.AccessService;
import com.masterswork.storage.service.SecurityUtils;
import com.masterswork.storage.service.exception.InvalidAccessException;
import com.masterswork.storage.service.exception.StorageFileNotFoundException;
import com.masterswork.storage.service.mapper.FileAccessPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private final StoredFileRepository storedFileRepository;
    private final FileAccessPermissionRepository fileAccessPermissionRepository;
    private final AccountClient accountClient;
    private final FileAccessPermissionMapper fileAccessPermissionMapper;

    @Override
    public void grantAccess(Long id, FileAccessPermissionDTO fileAccessPermissionDTO) {
        StoredFile storedFile = storedFileRepository.findById(id)
                .orElseThrow(() -> new StorageFileNotFoundException("Could not read file with Id: " + id));

        validateCanGrantAccess(storedFile, SecurityUtils.getCurrentUserUsername());

        String username = fileAccessPermissionDTO.getUsername();
        FilePermissionType permission = fileAccessPermissionDTO.getPermissionType();

        if (storedFile.hasAccess(permission, username)) {
            String message = String.format("User already [%s] has permission to file with id: [%s]", username, id);
            throw new InvalidAccessException(message);
        }

        storedFile.addPermission(FileAccessPermission.of(permission, username));
        storedFileRepository.save(storedFile);
    }

    @Override
    public void revokeAccess(Long id, FileAccessPermissionDTO fileAccessPermissionDTO) {
        StoredFile storedFile = storedFileRepository.findById(id)
                .orElseThrow(() -> new StorageFileNotFoundException("Could not read file with Id: " + id));
        validateCanGrantAccess(storedFile, SecurityUtils.getCurrentUserUsername());

        String username = fileAccessPermissionDTO.getUsername();
        FilePermissionType permission = fileAccessPermissionDTO.getPermissionType();

        if (!storedFile.hasAccess(permission, username)) {
            String message = String.format("User [%s] doesn't have permission to file with id: [%s]", username, id);
            throw new InvalidAccessException(message);
        }

        storedFile.revokePermission(username, permission);
        storedFileRepository.save(storedFile);
    }

    @Override
    public void grantFilesAccessForAppUsers(
            Set<Long> fileIds, Set<Long> appUserIds, FileAccessLevelDTO fileAccessPermissionDTO) {
        List<StoredFile> fileList = storedFileRepository.findAllByIdIn(fileIds);
        String currentUserUsername = SecurityUtils.getCurrentUserUsername();
        fileList.forEach(file -> validateCanGrantAccess(file, currentUserUsername));

        Set<String> usernamesForAppUsers = accountClient.getUsernamesForAppUsers(appUserIds);
        FilePermissionType permission = fileAccessPermissionDTO.getPermissionType();
        for (StoredFile file : fileList) {
            for (String username : usernamesForAppUsers) {
                if (!file.hasAccess(permission, username)) {
                    file.addPermission(FileAccessPermission.of(permission, username));
                }
            }
        }
        storedFileRepository.saveAll(fileList);
    }

    @Override
    public Page<FileAccessPermissionDTO> getUserFilePermissionsById(Long fileId, String username, Pageable pageable) {
        validateCanReadFileAccess(fileId);
        Page<FileAccessPermission> permissions = username == null ?
                fileAccessPermissionRepository.findAllByFile_Id(fileId, pageable) :
                fileAccessPermissionRepository.findAllByFile_IdAndUsername(fileId, username, pageable);
        return permissions.map(fileAccessPermissionMapper::toDto);
    }
    
    private void validateCanGrantAccess(StoredFile storedFile, String username) {
        boolean isAdmin = SecurityUtils.hasRole(RoleName.ADMIN.getName());
        boolean isOwner = Objects.equals(storedFile.getOwner(), username);
        if (!isAdmin && !isOwner) {
            String message = String.format(
                    "User [%s] doesnt have grant permissions for file: [%s]", username, storedFile.getId());
            throw new AccessDeniedException(message);
        }
    }

    private void validateCanReadFileAccess(Long fileId) {
        boolean isAdmin = SecurityUtils.hasRole(RoleName.ADMIN.getName());
        boolean isOwner = storedFileRepository.existsByOwnerAndId(SecurityUtils.getCurrentUserUsername(), fileId);
        if (!isAdmin && !isOwner) {
            String message = String.format(
                    "User [%s] doesnt can't read permissions for file with id: [%s]", SecurityUtils.getCurrentUserUsername(), fileId);
            throw new AccessDeniedException(message);
        }
    }
}

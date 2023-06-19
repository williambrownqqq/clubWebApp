package com.masterswork.storage.service;

import com.masterswork.storage.api.dto.access.FileAccessLevelDTO;
import com.masterswork.storage.api.dto.access.FileAccessPermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

public interface AccessService {

    void grantAccess(Long id, FileAccessPermissionDTO fileAccessPermissionDTO);

    void revokeAccess(Long id, FileAccessPermissionDTO fileAccessPermissionDTO);

    void grantFilesAccessForAppUsers(Set<Long> fileIds, Set<Long> appUserIds, FileAccessLevelDTO fileAccessPermissionDTO);

    Page<FileAccessPermissionDTO> getUserFilePermissionsById(Long fileId, String username, Pageable pageable);
}

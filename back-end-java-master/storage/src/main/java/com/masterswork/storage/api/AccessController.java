package com.masterswork.storage.api;

import com.masterswork.storage.api.dto.access.FileAccessLevelDTO;
import com.masterswork.storage.api.dto.access.FileAccessPermissionDTO;
import com.masterswork.storage.service.AccessService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/access")
@RequiredArgsConstructor
public class AccessController {

    private final AccessService accessService;

    @PostMapping("/{id}/grant")
    public ResponseEntity<?> grantAccess(@PathVariable Long id, @RequestBody FileAccessPermissionDTO fileAccessPermissionDTO) {
        accessService.grantAccess(id, fileAccessPermissionDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/revoke")
    public ResponseEntity<?> revokeAccess(@PathVariable Long id, @RequestBody FileAccessPermissionDTO fileAccessPermissionDTO) {
        accessService.revokeAccess(id, fileAccessPermissionDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/grant")
    public ResponseEntity<?> grantFilesAccessForAppUsers(
            @RequestParam Set<Long> fileIds, @RequestParam Set<Long> appUserIds, @RequestBody FileAccessLevelDTO fileAccessPermissionDTO) {
        accessService.grantFilesAccessForAppUsers(fileIds, appUserIds, fileAccessPermissionDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<FileAccessPermissionDTO>> getAllFilePermissionsForUser(
            @PathVariable Long id, @RequestParam(name = "username", required = false) String username,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(accessService.getUserFilePermissionsById(id, username, pageable));
    }
}

package com.masterswork.storage.api;

import com.masterswork.storage.api.dto.file.StoredFileDTO;
import com.masterswork.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLConnection;


@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<StoredFileDTO>> listAllUploadedFiles(
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(storageService.getAllStoredFiles(pageable));
    }

    @GetMapping("/current")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<StoredFileDTO>> listAllFilesForUser(
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(storageService.getCurrentUserStoredFiles(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Resource> serveFile(@PathVariable Long id) {
        Resource file = storageService.loadAsResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/preview/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Resource> previewFile(@PathVariable Long id) {
        Resource file = storageService.loadAsResource(id);
        String mimeType = URLConnection.guessContentTypeFromName(file.getFilename());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore().mustRevalidate())
                .contentType(MediaType.valueOf(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteFileById(@PathVariable Long id) {
        storageService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StoredFileDTO> updateFileById(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        var storedFileDTO = storageService.updateFile(file, id);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedFileDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(storedFileDTO);
    }


    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StoredFileDTO> handleFileUpload(@RequestParam("file") MultipartFile file) {
        var storedFileDTO = storageService.store(file);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedFileDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(storedFileDTO);
    }
}

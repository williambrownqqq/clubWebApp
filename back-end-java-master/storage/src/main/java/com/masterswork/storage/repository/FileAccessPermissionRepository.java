package com.masterswork.storage.repository;

import com.masterswork.storage.model.FileAccessPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAccessPermissionRepository extends JpaRepository<FileAccessPermission, Long> {

    Page<FileAccessPermission> findAllByFile_Id(Long fileId, Pageable pageable);

    Page<FileAccessPermission> findAllByFile_IdAndUsername(Long fileId, String username, Pageable pageable);
}

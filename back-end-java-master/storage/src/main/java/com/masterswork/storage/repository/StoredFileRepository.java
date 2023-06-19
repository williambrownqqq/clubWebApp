package com.masterswork.storage.repository;

import com.masterswork.storage.model.StoredFile;
import com.masterswork.storage.model.enumeration.FilePermissionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {

    boolean existsByOwnerAndId(String owner, Long id);

    Optional<StoredFile> findByOriginalFilenameAndOwner(String originalFilename, String owner);

    @Query("select distinct file " +
            " from StoredFile as file " +
            "   left join FileAccessPermission as permission on file.id = permission.file.id " +
            " where file.owner = ?1 or (permission.permissionType = ?2 and permission.username = ?1) ")
    Page<StoredFile> findAllByUserAndPermissionType(String username, FilePermissionType filePermissionType, Pageable pageable);

    List<StoredFile> findAllByIdIn(Collection<Long> ids);
}

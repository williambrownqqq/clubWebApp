package com.masterswork.storage.model;

import com.masterswork.storage.model.base.AuditedEntity;
import com.masterswork.storage.model.enumeration.FilePermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_access_permission")
public class FileAccessPermission extends AuditedEntity {

    @Id
    @Column(name = "file_access_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "permission_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FilePermissionType permissionType;

    @ManyToOne
    @JoinColumn(name = "stored_file_id", nullable = false)
    private StoredFile file;

    public static FileAccessPermission of(FilePermissionType filePermissionType, String username) {
        return FileAccessPermission.builder()
                .permissionType(filePermissionType)
                .username(username)
                .build();
    }
}

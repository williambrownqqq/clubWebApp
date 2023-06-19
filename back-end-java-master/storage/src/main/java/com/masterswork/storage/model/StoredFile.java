package com.masterswork.storage.model;

import com.masterswork.storage.model.base.AuditedEntity;
import com.masterswork.storage.model.enumeration.FilePermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stored_file")
public class StoredFile extends AuditedEntity {

    @Id
    @Column(name = "stored_file_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "version", nullable = false)
    @ColumnDefault("1")
    @Builder.Default
    private Long version = 1L;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FileAccessPermission> fileAccessPermissions = new ArrayList<>();

    public StoredFile incrementVersion() {
        version++;
        return this;
    }

    public StoredFile addPermission(FileAccessPermission permission) {
        fileAccessPermissions.add(permission);
        permission.setFile(this);
        return this;
    }

    public StoredFile revokePermission(String username, FilePermissionType permissionType) {
        fileAccessPermissions.removeIf(permission ->
                Objects.equals(permission.getUsername(), username) &&
                Objects.equals(permission.getPermissionType(), permissionType)
        );
        return this;
    }

    public boolean hasAccess(FilePermissionType permissionType, String username) {
        return Objects.equals(owner, username) || fileAccessPermissions.stream()
                .anyMatch(fileAccessPermission ->
                    Objects.equals(fileAccessPermission.getUsername(), username) &&
                    Objects.equals(fileAccessPermission.getPermissionType(), permissionType)
                );
    }
}

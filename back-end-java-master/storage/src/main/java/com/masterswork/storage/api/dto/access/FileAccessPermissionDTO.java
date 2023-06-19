package com.masterswork.storage.api.dto.access;

import com.masterswork.storage.model.enumeration.FilePermissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class FileAccessPermissionDTO {

    private FilePermissionType permissionType;

    private String username;
}

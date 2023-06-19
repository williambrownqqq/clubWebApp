package com.masterswork.account.service;

import com.masterswork.account.api.dto.role.RoleCreateDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.api.dto.role.RoleUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    RoleResponseDTO createRole(RoleCreateDTO facultyResponseDTO);

    RoleResponseDTO updateRole(Long id, RoleUpdateDTO facultyResponseDTO);

    RoleResponseDTO patchRole(Long id, RoleUpdateDTO facultyResponseDTO);

    Page<RoleResponseDTO> getAllRoles(Pageable pageable);

    Page<RoleResponseDTO> getAllRolesByAccountId(Long accountId, Pageable pageable);

    Page<RoleResponseDTO> getAllRolesByUsername(String username, Pageable pageable);

    RoleResponseDTO getRole(Long id);

    void deleteRole(Long id);
}

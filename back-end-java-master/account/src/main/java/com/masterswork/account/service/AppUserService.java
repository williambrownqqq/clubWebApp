package com.masterswork.account.service;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;
import com.masterswork.account.model.enumeration.PersonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface AppUserService {

    AppUserResponseDTO createAppUserForAccount(Long accountId, AppUserCreateDTO appUserCreateDTO);

    AppUserResponseDTO assignCathedra(Long userId, Long cathedraId);

    AppUserResponseDTO unassignCathedra(Long userId, Long cathedraId);

    AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO);

    AppUserResponseDTO updateUser(Long userId, AppUserUpdateDTO appUserUpdateDTO);

    AppUserResponseDTO patchUser(Long userId, AppUserUpdateDTO appUserUpdateDTO);

    AppUserResponseDTO getUserById(Long userId);

    AppUserResponseDTO getUserByAccountUsername(String username);

    Page<AppUserResponseDTO> getAllAppUsers(Pageable pageable);

    Page<AppUserResponseDTO> getAllAppUsersByType(PersonType personType, Pageable pageable);

    Set<String> getAllUserTypes();

    void deleteAppUserById(Long userId);

    Set<String> getUserNamesByIds(Set<Long> userId);
}

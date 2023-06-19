package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;
import com.masterswork.account.model.Account;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.Cathedra;
import com.masterswork.account.model.enumeration.PersonType;
import com.masterswork.account.repository.AccountRepository;
import com.masterswork.account.repository.AppUserRepository;
import com.masterswork.account.repository.CathedraRepository;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AccountRepository accountRepository;
    private final CathedraRepository cathedraRepository;
    private final AppUserMapper appUserMapper;

    @Override
    public AppUserResponseDTO createAppUserForAccount(Long accountId, AppUserCreateDTO appUserCreateDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("No account with id: " + accountId));
        AppUser newEntity = appUserMapper.createFrom(appUserCreateDTO);
        newEntity.setAccount(account);
        account.setUser(newEntity);
        accountRepository.save(account);
        return appUserMapper.toDto(appUserRepository.save(newEntity));
    }

    @Override
    public AppUserResponseDTO assignCathedra(Long userId, Long cathedraId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id: " + cathedraId));

        appUser.addCathedra(cathedra);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO unassignCathedra(Long userId, Long cathedraId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id: " + cathedraId));

        appUser.removeCathedra(cathedra);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO createAppUser(AppUserCreateDTO appUserCreateDTO) {
        var newEntity = appUserMapper.createFrom(appUserCreateDTO);
        return appUserMapper.toDto(appUserRepository.save(newEntity));
    }

    @Override
    public AppUserResponseDTO updateUser(Long userId, AppUserUpdateDTO appUserUpdateDTO) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));

        appUserMapper.updateFrom(appUser, appUserUpdateDTO);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO patchUser(Long userId, AppUserUpdateDTO appUserUpdateDTO) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));

        appUserMapper.patchFrom(appUser, appUserUpdateDTO);

        return appUserMapper.toDto(appUserRepository.save(appUser));
    }

    @Override
    public AppUserResponseDTO getUserById(Long userId) {
        return appUserRepository.findById(userId)
                .map(appUserMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));
    }

    @Override
    public AppUserResponseDTO getUserByAccountUsername(String username) {
        return appUserRepository.findByAccount_Username(username)
                .map(appUserMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No appUser associated with username: " + username));
    }

    @Override
    public Page<AppUserResponseDTO> getAllAppUsers(Pageable pageable) {
        return appUserRepository.findAll(pageable).map(appUserMapper::toDto);
    }

    @Override
    public Page<AppUserResponseDTO> getAllAppUsersByType(PersonType personType, Pageable pageable) {
        return appUserRepository.findAllByType(personType, pageable).map(appUserMapper::toDto);
    }

    @Override
    public Set<String> getAllUserTypes() {
        return PersonType.getAllTypesNames();
    }

    @Override
    public void deleteAppUserById(Long userId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No appUser with id: " + userId));

        appUserRepository.delete(appUser);
    }

    @Override
    public Set<String> getUserNamesByIds(Set<Long> userId) {
        return accountRepository.findAllByUserIdIn(userId).stream()
                .map(Account::getUsername)
                .collect(Collectors.toSet());
    }
}

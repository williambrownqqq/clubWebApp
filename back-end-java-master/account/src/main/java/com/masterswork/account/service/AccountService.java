package com.masterswork.account.service;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.api.dto.account.AccountUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    AccountResponseDTO updateAccount(Long accountId, AccountUpdateDTO accountUpdateDTO);

    AccountResponseDTO patchAccount(Long accountId, AccountUpdateDTO accountUpdateDTO);

    AccountResponseDTO addRoleToAccount(Long accountId, Long roleId);

    AccountResponseDTO removeRoleFromAccount(Long accountId, Long roleId);

    AccountResponseDTO addAppUserToAccount(Long accountId, Long appUserId);

    AccountResponseDTO unassignAppUserFromAccount(Long accountId);

    Page<AccountResponseDTO> getAllAccounts(Pageable pageable);

    AccountResponseDTO getAccountByUsername(String username);

    AccountResponseDTO getAccountById(Long id);

}

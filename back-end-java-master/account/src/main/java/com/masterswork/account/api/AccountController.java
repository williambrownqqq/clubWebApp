package com.masterswork.account.api;

import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.api.dto.account.AccountUpdateDTO;
import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.role.RoleResponseDTO;
import com.masterswork.account.service.AccountService;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AppUserService appUserService;
    private final RoleService roleService;

    @Operation(summary = "Create new appUser and assign to account")
    @PreAuthorize("hasRole('ADMIN') or #accountId == authentication.principal.accountId")
    @PostMapping(path = "/{accountId}/app-users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> createAppUserForAccount(@PathVariable Long accountId, @Valid @RequestBody AppUserCreateDTO body) {
        return ResponseEntity.ok(appUserService.createAppUserForAccount(accountId, body));
    }

    @Operation(summary = "Update account by accountId")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{accountId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long accountId, @Valid @RequestBody AccountUpdateDTO body) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, body));
    }

    @Operation(summary = "Patch account by accountId")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/{accountId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> patchAccount(@PathVariable Long accountId, @RequestBody AccountUpdateDTO body) {
        return ResponseEntity.ok(accountService.patchAccount(accountId, body));
    }

    @Operation(summary = "Add role to account")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{accountId}/roles/{roleId}", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> addRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.addRoleToAccount(accountId, roleId));
    }

    @Operation(summary = "Remove role from account")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{accountId}/roles/{roleId}", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> removeRole(@PathVariable Long accountId, @PathVariable Long roleId) {
        return ResponseEntity.ok(accountService.removeRoleFromAccount(accountId, roleId));
    }

    @Operation(summary = "Assign appUser to account")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{accountId}/app-users/{appUserId}", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> addAppUser(@PathVariable Long accountId, @PathVariable Long appUserId) {
        return ResponseEntity.ok(accountService.addAppUserToAccount(accountId, appUserId));
    }

    @Operation(summary = "Unassign appUser from account")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{accountId}/app-users", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> unassignAppUser(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.unassignAppUserFromAccount(accountId));
    }

    @Operation(summary = "Get all accounts")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable));
    }

    @Operation(summary = "Get account of the current user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/current", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> getMyAccount(Authentication authentication) {
        return ResponseEntity.ok(accountService.getAccountByUsername(authentication.getName()));
    }

    @Operation(summary = "Get account by username")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/username/{username}", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> getAccountByUsername(@PathVariable String username) {
        return ResponseEntity.ok(accountService.getAccountByUsername(username));
    }

    @Operation(summary = "Get account by accountId")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/{accountId}", produces = "application/json")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @Operation(summary = "Get roles assigned to account by accountId")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/{accountId}/roles", produces = "application/json")
    public ResponseEntity<Page<RoleResponseDTO>> getAllRolesForAccount(
            @PathVariable Long accountId, @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(roleService.getAllRolesByAccountId(accountId, pageable));
    }

    @Operation(summary = "Get roles assigned to account by username")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/username/{username}/roles", produces = "application/json")
    public ResponseEntity<Page<RoleResponseDTO>> getAllRolesForAccountByUsername(
            @PathVariable String username, @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(roleService.getAllRolesByUsername(username, pageable));
    }

}

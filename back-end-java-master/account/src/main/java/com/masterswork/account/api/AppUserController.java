package com.masterswork.account.api;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.api.dto.appuser.AppUserUpdateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.model.enumeration.PersonType;
import com.masterswork.account.service.AppUserService;
import com.masterswork.account.service.CathedraService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final CathedraService cathedraService;

    @Operation(summary = "Create new appUser")
    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> createAppUser(@Valid @RequestBody AppUserCreateDTO appUserCreateDTO) {
        return ResponseEntity.ok(appUserService.createAppUser(appUserCreateDTO));
    }

    @Operation(summary = "Assign cathedra to appUser")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{userId}/cathedras/{cathedraId}", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> assignCathedra(@PathVariable Long userId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(appUserService.assignCathedra(userId, cathedraId));
    }

    @Operation(summary = "Unassign cathedra from appUser")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{userId}/cathedras/{cathedraId}", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> unassignCathedra(@PathVariable Long userId, @PathVariable Long cathedraId) {
        return ResponseEntity.ok(appUserService.unassignCathedra(userId, cathedraId));
    }

    @Operation(summary = "Update appUser by userId")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.principal.appUserId)")
    @PutMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody AppUserUpdateDTO body) {
        return ResponseEntity.ok(appUserService.updateUser(userId, body));
    }

    @Operation(summary = "Patch appUser by userId")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.principal.appUserId)")
    @PatchMapping(path = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> patchUser(@PathVariable Long userId, @RequestBody AppUserUpdateDTO body) {
        return ResponseEntity.ok(appUserService.patchUser(userId, body));
    }

    @Operation(summary = "Get appUser by userId")
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(appUserService.getUserById(userId));
    }

    @Operation(summary = "Get appUser for current user")
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/current", produces = "application/json")
    public ResponseEntity<AppUserResponseDTO> getUserCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(appUserService.getUserByAccountUsername(authentication.getName()));
    }

    @Operation(summary = "Get all appUsers")
    @PreAuthorize("hasRole('USER')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<AppUserResponseDTO>> getAllAppUsers(
            @RequestParam(name = "type", required = false) PersonType personType,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(personType == null ?
                appUserService.getAllAppUsers(pageable) :
                appUserService.getAllAppUsersByType(personType, pageable)
        );
    }

    @Operation(summary = "Get cathedras for appUser by userId")
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{userId}/cathedras", produces = "application/json")
    public ResponseEntity<Page<CathedraResponseDTO>> getAllCathedrasForAppUser(
            @PathVariable Long userId,
            @ParameterObject @PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(cathedraService.getAllCathedrasByAppUserId(userId, pageable));
    }

    @Operation(summary = "Get all available types of users")
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/types", produces = "application/json")
    public ResponseEntity<Set<String>> getAllUserTypes() {
        return ResponseEntity.ok(appUserService.getAllUserTypes());
    }

    @Operation(summary = "Delete appUser by userId")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{userId}", produces = "application/json")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        appUserService.deleteAppUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/username", produces = "application/json")
    public ResponseEntity<Set<String>> getUsernamesForAppUsers(@RequestParam Set<Long> userId) {
        return ResponseEntity.ok(appUserService.getUserNamesByIds(userId));
    }
}

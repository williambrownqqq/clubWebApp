package com.masterswork.account.service.impl;

import com.masterswork.account.api.auth.dto.AuthorizationResponseDTO;
import com.masterswork.account.api.auth.dto.SignUpRequestDTO;
import com.masterswork.account.api.auth.dto.TokensResponseDTO;
import com.masterswork.account.api.dto.account.AccountResponseDTO;
import com.masterswork.account.jwt.JwtUtil;
import com.masterswork.account.model.Account;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.Role;
import com.masterswork.account.model.enumeration.RoleName;
import com.masterswork.account.repository.AccountRepository;
import com.masterswork.account.repository.RoleRepository;
import com.masterswork.account.service.AuthService;
import com.masterswork.account.service.exception.UserExistsException;
import com.masterswork.account.service.mapper.AccountMapper;
import com.masterswork.account.service.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final AppUserMapper appUserMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthorizationResponseDTO authenticateAndGenerateTokens(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return accountRepository.findFirstByUsername(username)
                .map(this::generateAuthorizationResponse)
                .orElseThrow(() -> new EntityNotFoundException("No user with username " + username));
    }

    @Override
    public AuthorizationResponseDTO refreshAccessToken(String refreshToken) {
        String username = jwtUtil.validateAndParseToken(refreshToken).getSubject();

        return accountRepository.findFirstByUsername(username)
                .map(this::generateAuthorizationResponse)
                .orElseThrow(() -> new EntityNotFoundException("No user with username " + username));
    }

    @Override
    public AuthorizationResponseDTO createUser(SignUpRequestDTO signUpRequestDTO) {
        checkIfUsernameOrEmailTaken(signUpRequestDTO.getUsername(), signUpRequestDTO.getEmail());

        Account newAccount = accountMapper.createFrom(signUpRequestDTO);
        AppUser appUser = appUserMapper.createFrom(signUpRequestDTO);
        Role userRole = roleRepository.findByName(RoleName.USER.getName());

        newAccount.setUser(appUser).addRole(userRole);

        final Account saved = accountRepository.save(newAccount);
        return generateAuthorizationResponse(saved);
    }

    private void checkIfUsernameOrEmailTaken(String username, String email) {
        boolean usernameTaken = accountRepository.existsByUsername(username);
        boolean emailTaken = accountRepository.existsByEmail(email);

        if (usernameTaken || emailTaken) {
            Set<String> messages = new HashSet<>();
            if (usernameTaken) messages.add("Username " + username + " is already taken");
            if (emailTaken) messages.add("Email " + email + " is already taken");

            throw new UserExistsException(String.join(" ", messages));
        }
    }

    private AuthorizationResponseDTO generateAuthorizationResponse(Account account) {
        AccountResponseDTO accountDTO = accountMapper.toDto(account);
        TokensResponseDTO tokensDTO = TokensResponseDTO.of(
                jwtUtil.generateAccessToken(account),
                jwtUtil.generateRefreshToken(account)
        );

        return AuthorizationResponseDTO.of(accountDTO, tokensDTO);
    }

}

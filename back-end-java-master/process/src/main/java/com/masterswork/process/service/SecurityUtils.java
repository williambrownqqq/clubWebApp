package com.masterswork.process.service;

import com.masterswork.process.config.principal.UserPrincipal;
import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@UtilityClass
public class SecurityUtils {

    private static final String ROLE_PREFIX = "ROLE_";

    public Optional<String> getJwt() {
        return Optional.ofNullable((AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
                .map(abstractAuthenticationToken -> (String) abstractAuthenticationToken.getCredentials());
    }

    public String getCurrentUserUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Principal::getName)
                .orElse("system");
    }

    public Long getCurrentAppUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(principal -> ((UserPrincipal)principal).getAppUserId())
                .orElse(null);
    }

    public boolean hasRole(String role) {
        Predicate<GrantedAuthority> matchesRolePredicate = grantedAuthority ->
                Objects.equals(grantedAuthority.getAuthority(), ROLE_PREFIX + role);
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(principal -> principal.getAuthorities().stream().anyMatch(matchesRolePredicate))
                .orElse(false);
    }
}

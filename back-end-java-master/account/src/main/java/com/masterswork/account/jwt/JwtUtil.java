package com.masterswork.account.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.masterswork.account.config.principal.UserPrincipal;
import com.masterswork.account.config.properties.JwtProperties;
import com.masterswork.account.model.Account;
import com.masterswork.account.model.AppUser;
import com.masterswork.account.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private Algorithm algorithm;

    @PostConstruct
    void setAlgorithm() {
        this.algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
    }

    public String generateAccessToken(Account account) {
        Function<Role, String> roleMapper = role -> "ROLE_" + role.getName();
        return JWT.create()
                .withSubject(account.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getAccessTokenExpiry()))
                .withClaim("scp", account.getRoles().stream().map(roleMapper).collect(Collectors.toList()))
                .withClaim("account_id", account.getId())
                .withClaim("user_id", Optional.ofNullable(account.getUser()).map(AppUser::getId).orElse(null))
                .sign(algorithm);
    }

    public String generateRefreshToken(Account account) {
        return JWT.create()
                .withSubject(account.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiry()))
                .sign(algorithm);
    }

    public Authentication getAuthenticationFromRawToken(String rawToken) {
        DecodedJWT decodedJWT = validateAndParseToken(rawToken);

        String username = decodedJWT.getSubject();
        Long userId = decodedJWT.getClaim("user_id").asLong();
        Long accountId = decodedJWT.getClaim("account_id").asLong();
        Collection<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("scp").asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        UserPrincipal userPrincipal = new UserPrincipal(accountId, userId, username);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }

    public DecodedJWT validateAndParseToken(String token) {
        return JWT.require(algorithm).build().verify(token);
    }

}

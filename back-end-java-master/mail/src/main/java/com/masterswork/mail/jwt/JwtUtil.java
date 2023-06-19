package com.masterswork.mail.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.masterswork.mail.config.principal.UserPrincipal;
import com.masterswork.mail.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
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
        return new UsernamePasswordAuthenticationToken(userPrincipal, rawToken, authorities);
    }

    public DecodedJWT validateAndParseToken(String token) {
        return JWT.require(algorithm).build().verify(token);
    }

    public void setUserAsAdmin(String username, Long userId) {
        UserPrincipal userPrincipal = new UserPrincipal(null, userId, username);
        String rawToken = generateAccessToken(username, userId);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userPrincipal, rawToken, null));
    }
    private String generateAccessToken(String username, Long userId) {
        String roleAdmin = "ROLE_ADMIN";
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getAccessTokenExpiry()))
                .withClaim("scp", List.of(roleAdmin))
                .withClaim("user_id", userId)
                .sign(algorithm);
    }

}

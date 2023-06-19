package com.zanchenko.alex.Alex.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {

    private String jwtSigningKey = "secret";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasCLaim(String token, String claimName){
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token); // this method will get all the claims we have within our token
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJwt(token).getBody(); // then we just try to extract the required token that we want
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims){ // claims are the extra information
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis())) // date of the token
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24))) // and the expiration Date // the token which would be generated will expire in 24 hours
                .signWith(SignatureAlgorithm.HS256,jwtSigningKey).compact(); // define the signature alghoritm HS256 and using a JWT signing key
    }

    public Boolean isTokenValid(String token, UserDetails userDetails){ // token valid
        final String username = extractUsername(token); // we'll take the token itself and the user details all the object or the user object to validate it
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // we will extract the username first off
        // and then we will compare and validate the JWT token data with the current user
        // so here if user equals user that's comming from our userdetails and we need to make sure that the token is not expired
    }
}

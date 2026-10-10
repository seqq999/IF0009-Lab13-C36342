package com.medpharm.medpharm_backend.security;

import java.util.Date;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final SecretKey key;
    private final long expirationMs;

        public JwtUtils(@Value("${jwt.secret}") String secret,
            @Value("${jwt.expirationMs}") long expirationMs){
                if(secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32){
                    throw new IllegalArgumentException("app.jwt.secret debe tener al menos 32 bytes");
                    }
                    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    this.expirationMs = expirationMs;
                }

    public String generateToken(Authentication authentication) {
        Date now = new Date();

        return Jwts.builder().subject(authentication.getName()).issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs)).signWith(key).compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Boolean isValid(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public long getExpirationMs(){ return expirationMs;}

}

package edu.esoft.com.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /* ---------- Token creation ---------- */
    public String generateToken(String userId, String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role",  role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                // key first, then algorithm (SecureMacAlgorithm) – works in 0.12.x
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    /* ---------- Extractors & validators ---------- */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, String email) {
        return email.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /* ---------- Internal helper ---------- */
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

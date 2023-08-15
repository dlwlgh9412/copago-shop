package com.copago.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils {
    private static final String AUDIENCE_MOBILE = "mobile";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getIssuedAtDate(String token) {
        return getClaim(token, Claims::getIssuedAt);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public String getAudience(String token) {
        return getClaim(token, Claims::getAudience);
    }

    public void getClaim(String token) {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    public <T> T getClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claimResolver.apply(claims);
    }

    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return doGenerateToken(claims, userId);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = calculateExpirationDate(now);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(JwtTokenUtils.AUDIENCE_MOBILE)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private Date calculateExpirationDate(Date createdAt) {
        return new Date(createdAt.getTime() + expiration * 1000);
    }
}

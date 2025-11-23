package com.foodfast.user_service.utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.foodfast.user_service.model.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .claim("phone", user.getPhone())
                .claim("fullname", user.getFullname())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

public Map<String, Object> getUserFromToken(String token) {
    var claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

    Map<String, Object> userDetails = new HashMap<>();
    userDetails.put("id", claims.getSubject());
    userDetails.put("email", claims.get("email"));
    userDetails.put("phone", claims.get("phone"));
    userDetails.put("fullname", claims.get("fullname"));
    userDetails.put("role", claims.get("role"));
    return userDetails;
}

}

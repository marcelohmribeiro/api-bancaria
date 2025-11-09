package com.banco.api_java.utils;

import com.banco.api_java.config.JwtConfig;
import com.banco.api_java.dtos.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(UserDTO user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.email())
                .claim("id", user.id())
                .claim("name", user.name())
                .claim("role", user.role())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .signWith(jwtConfig.getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}

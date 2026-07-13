package com.cherrybite.config;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.cherrybite.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

  private final JwtProperties jwtProperties;
  private final SecretKey key;

  public JwtProvider(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
    this.key = Keys.hmacShaKeyFor(jwtProperties.getJwtSecret().getBytes());
  }

  public SecretKey getKey() {
    return key;
  }

  public String generateToken(User user) {

    return Jwts.builder().issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 86400000))
        .claim("userId", user.getUserId().toString()).claim("username", user.getUserName())
        .claim("role", user.getUserRole().name()).signWith(key).compact();
  }

  public String generateRefreshToken() {
    return UUID.randomUUID().toString();
  }

}

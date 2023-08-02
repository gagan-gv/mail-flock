package com.example.mailflock.services.interfaces;

import io.jsonwebtoken.Claims;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface IJWTService {
    public String extractUsername(String jwtToken);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails,
            long expirationValue);
    public String generateToken(UserDetails userDetails);
    public String generateRefreshToken(UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);
}

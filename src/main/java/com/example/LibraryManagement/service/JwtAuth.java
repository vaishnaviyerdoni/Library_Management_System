package com.example.LibraryManagement.service;

import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuth {
    
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //Token Generation
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)                 // "sub"
                .claim("role", role)                  // custom claim
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hr expiry
                .signWith(key)                        // sign with secret key
                .compact();
    }
}

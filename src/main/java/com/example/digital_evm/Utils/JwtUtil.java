package com.example.digital_evm.Utils ;

import com.example.digital_evm.Service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Sir ke code wala secret key format
    private String SECRET_KEY = "Tak+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    // 1. Secret key ko cryptographic key me badalna (Sir ka style)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 2. Token me se username nikalna (Seedha claims se)
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // 3. Token me se expiry date nikalna
    public Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    // 4. Token ko decode karke saare claims (data box) bahar nikalna
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 5. Check karna ki token expire hua hai ya nahi
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 6. Token generate karna (Map use karke)[cite: 1]
    public String generateToken(UserDetails userDeatails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDeatails.getAuthorities());
        return createToken(claims,userDeatails.getUsername());
    }

    // 7. Token banane ka main method (Header aur signature ke sath)[cite: 2]
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ", "JWT").and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 Minutes expiry (jaise sir ka hai)[cite: 2]
                .signWith(getSigningKey())
                .compact();
    }

    // 8. Token validate karna (Sir ke style me direct username ke sath)
    public Boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
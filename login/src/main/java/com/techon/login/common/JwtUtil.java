package com.techon.login.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final String SECRET_KEY = "techonMesSecretKeyechonMesSecretKeyechonMesSecretKey1234!";
  private static final long EXPIRATION_TIME = 864_000_000; // 10 days
  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // Generate token
  public String generateToken(Long userSeq, String type, long expirationTimeInMillis) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("type", type);
    claims.put("userSeq", userSeq);
    return createToken(claims, userSeq.toString(), expirationTimeInMillis);
  }

  // Create the token
  private String createToken(Map<String, Object> claims, String subject, long expirationTimeInMillis) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  // Extract userSeq from token
  public Long extractUserSeq(String token) {
    return Long.parseLong(extractClaim(token, Claims::getSubject));
  }

  // Extract a claim
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  // Check if the token is expired
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Validate token
  public Boolean validateToken(String token, Long userSeq) {
    final Long extractedUserSeq = extractUserSeq(token);
    return (extractedUserSeq.equals(userSeq) && !isTokenExpired(token));
  }
}

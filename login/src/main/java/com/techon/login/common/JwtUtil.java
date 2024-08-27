package com.techon.login.common;

import com.techon.login.entity.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

  private final String SECRET_KEY = "techonMesSecretKeyechonMesSecretKeyechonMesSecretKey1234!";
  private static final long EXPIRATION_TIME = 864_000_000; // 10 days
  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  // Generate token
  public String generateToken(Long userSeq, String type, long expirationTimeInMillis, List<Authority> roles) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("type", type);
    claims.put("userSeq", userSeq);
    claims.put("roles", extractRoleNames(roles)); // Store role names as strings
    return createToken(claims, userSeq.toString(), expirationTimeInMillis);
  }


  // Helper method to extract role names from Authority objects
  private List<String> extractRoleNames(List<Authority> roles) {
    return roles.stream()
        .map(Authority::getRole) // Assuming Authority has a getRoleName() method
        .collect(Collectors.toList());
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

  // Extract roles from token
  public List<String> extractRoles(String token) {
    Claims claims = extractAllClaims(token);
    return claims.get("roles", List.class); // This remains as it is
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

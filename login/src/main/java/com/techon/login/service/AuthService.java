package com.techon.login.service;

import com.techon.login.common.JwtProperties;
import com.techon.login.common.JwtUtil;
import com.techon.login.dto.TokenResponse;
import com.techon.login.entity.Authority;
import com.techon.login.entity.Member;
import com.techon.login.entity.Token;
import com.techon.login.repository.AuthorityRepository;
import com.techon.login.repository.TokenRepository;
import com.techon.login.repository.MemberRepository;  // Ensure this is your correct repository package
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthService {


  private final AuthenticationManager authenticationManager;

  private final JwtUtil jwtUtil;

  private final MemberRepository memberRepository;  // Replace CustomUserDetailsService with MemberRepository

  private final TokenRepository tokenRepository;

  private final AuthorityRepository authorityRepository;

  @Transactional
  public TokenResponse login(String nameid, String password) {

    // Load the Member entity based on the username (this replaces CustomUserDetailsService)
    Optional<Member> member = memberRepository.findByNameidAndDeletedAtIsNull(nameid);

    if(member.isEmpty()) {
      throw new RuntimeException("User Not Found");
    }

    Long userSeq = member.get().getId();

    // Check if a token already exists for the member
    Optional<Token> existingToken = tokenRepository.findByMember(member.get());

    // If a token exists, delete it
    existingToken.ifPresent(tokenRepository::delete);

    List<Authority> roleList = authorityRepository.findByMember(member.get());

    // Generate AccessToken (AC) and RefreshToken (RF)
    String accessToken = jwtUtil.generateToken(userSeq, "AC", JwtProperties.ACCESS_TOKEN_EXPIRATION, roleList);
    String refreshToken = jwtUtil.generateToken(userSeq, "RF", JwtProperties.REFRESH_TOKEN_EXPIRATION, roleList);

    // Save the new tokens to the token table
    Token token = Token.builder()
        .member(member.get())  // Directly reference the member entity
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .accessExpiry(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_TOKEN_EXPIRATION))
        .refreshExpiry(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_TOKEN_EXPIRATION))
        .build();

    tokenRepository.save(token);

    // Return the tokens to the client
    return new TokenResponse(member.get(), accessToken, refreshToken);
  }
}

package com.techon.login.service;

import com.techon.login.entity.Token;
import com.techon.login.repository.TokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

  private final TokenRepository tokenRepository;


  public Optional<Token> findByAccessToken(String accessToken) {
    return tokenRepository.findByAccessToken(accessToken);
  }

}

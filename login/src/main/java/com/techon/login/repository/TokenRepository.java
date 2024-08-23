package com.techon.login.repository;

import com.techon.login.entity.Member;
import com.techon.login.entity.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
  Optional<Token> findByAccessToken(String accessToken);
  Optional<Token> findByMember(Member member);
}

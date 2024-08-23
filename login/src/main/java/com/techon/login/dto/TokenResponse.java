package com.techon.login.dto;

import com.techon.login.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
  private Member member;
  private String accessToken;
  private String refreshToken;
}

package com.techon.login.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  INVALID_TOKEN("INVALID_TOKEN", "Invalid token"),
  EXPIRED_TOKEN("EXPIRED_TOKEN", "Expired token"),
  UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", "Unauthorized access");

  private final String code;
  private final String message;
}

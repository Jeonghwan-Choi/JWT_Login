package com.techon.login.common;

public class JwtProperties {
  public static final long ACCESS_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L; // 7 days
  public static final long REFRESH_TOKEN_EXPIRATION = 30 * 24 * 60 * 60 * 1000L; // 30 days
}


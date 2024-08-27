package com.techon.login.controller;

import com.techon.login.dto.LoginRequest;
import com.techon.login.dto.RegistrationRequest;
import com.techon.login.dto.TokenResponse;
import com.techon.login.entity.Member;
import com.techon.login.service.AuthService;
import com.techon.login.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  private final RegistrationService registrationService;


  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
    try {
      TokenResponse tokenResponse = authService.login(loginRequest.getNameid(), loginRequest.getPassword());
      return ResponseEntity.ok(tokenResponse);
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }

  @PostMapping("/register")
  public ResponseEntity<Member> register(@RequestBody RegistrationRequest request) {
    return ResponseEntity.ok(registrationService.register(request));
  }
}

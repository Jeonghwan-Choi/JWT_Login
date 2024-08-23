package com.techon.login.controller;

import com.techon.login.dto.RegistrationRequest;
import com.techon.login.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RegistrationController {

  @Autowired
  private RegistrationService registrationService;


}

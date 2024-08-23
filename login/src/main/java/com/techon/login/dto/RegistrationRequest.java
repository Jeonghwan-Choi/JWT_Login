package com.techon.login.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
  private String name;
  private String nameid;
  private String password;
  private String part;
  private String birth;
  private String gender;
  private String email;
  private String phone;
  private Integer salary;
  private List<String> role;

  // Lombok will generate getters and setters
}

package com.techon.login.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrantRole {

  private Long memberId;
  private String role;

}

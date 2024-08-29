package com.techon.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "role")
public class Role {

  @Id
  private String roleId;  // This field should correspond to the 'role_id' column in the database.

  private String roleNameKr;
  private String roleNameEn;

  // Getters and Setters
}

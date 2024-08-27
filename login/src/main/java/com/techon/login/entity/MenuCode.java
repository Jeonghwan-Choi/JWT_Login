package com.techon.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menu_code")
public class MenuCode {

  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "name_kr")
  private String nameKr;

  @Column(name = "name_en")
  private String nameEn;

  // Getters and Setters
}

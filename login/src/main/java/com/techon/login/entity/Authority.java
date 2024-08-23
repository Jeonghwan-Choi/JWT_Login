package com.techon.login.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "authority")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(nullable = false)
  private String role;

  // Optional custom constructor
  public Authority(Long id, Member member, String role) {
    this.id = id;
    this.member = member;
    this.role = role;
  }
}

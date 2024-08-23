package com.techon.login.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(nullable = false)
  private String accessToken;

  @Column(nullable = false)
  private String refreshToken;

  @Column(nullable = false)
  private Date accessExpiry;

  @Column(nullable = false)
  private Date refreshExpiry;

  // Optional custom constructor
  public Token(Long id, Member member, String accessToken, String refreshToken, Date accessExpiry, Date refreshExpiry) {
    this.id = id;
    this.member = member;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessExpiry = accessExpiry;
    this.refreshExpiry = refreshExpiry;
  }
}

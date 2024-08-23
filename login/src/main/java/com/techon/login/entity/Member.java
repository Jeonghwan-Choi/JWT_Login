package com.techon.login.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "nameid", nullable = false)
  private String nameid;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "part")
  private String part;

  @Column(name = "birth")
  private String birth;

  @Column(name = "gender")
  private String gender;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "salary")
  private Integer salary;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Authority> authorities = new ArrayList<>();

  // Correct the method name to getAuthorities
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (authorities == null) {
      authorities = new ArrayList<>();  // Ensure it's never null
    }
    return authorities.stream()
        .map(role -> new SimpleGrantedAuthority(role.getRole()))
        .collect(Collectors.toList());
  }

}

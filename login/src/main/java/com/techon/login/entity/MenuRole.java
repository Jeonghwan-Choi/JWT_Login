package com.techon.login.entity;

import com.techon.login.entity.id.MenuRoleId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "menu_role")
public class MenuRole {

  @EmbeddedId
  private MenuRoleId id;

  @ManyToOne
  @MapsId("parent")
  @JoinColumn(name = "parent", referencedColumnName = "code")
  private MenuCode parent;

  @ManyToOne
  @MapsId("child")
  @JoinColumn(name = "child", referencedColumnName = "code")
  private MenuCode child;

  @ManyToOne
  @MapsId("roleId")
  @JoinColumn(name = "role_id", referencedColumnName = "roleId")  // Make sure this references 'roleId' in Role entity
  private Role role;

  // Getters and Setters
}

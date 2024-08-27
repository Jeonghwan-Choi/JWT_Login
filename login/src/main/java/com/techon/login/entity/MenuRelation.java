package com.techon.login.entity;

import com.techon.login.entity.id.MenuRelationId;
import jakarta.persistence.*;

@Entity
@Table(name = "menu_relation")
@IdClass(MenuRelationId.class) // Composite key handling
public class MenuRelation {

  @Id
  private int seq;

  @Id
  @ManyToOne
  @JoinColumn(name = "parent", referencedColumnName = "code")
  private MenuCode parent;

  @Id
  @ManyToOne
  @JoinColumn(name = "child", referencedColumnName = "code")
  private MenuCode child;

  private String page;

  // Getters and Setters
}

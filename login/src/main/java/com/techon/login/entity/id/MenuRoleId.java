package com.techon.login.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuRoleId implements Serializable {

  private int seq;
  private String parent;
  private String child;
  private String roleId;

  // Default constructor, equals, and hashCode methods
}

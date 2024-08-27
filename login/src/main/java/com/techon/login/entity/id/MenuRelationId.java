package com.techon.login.entity.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class MenuRelationId implements Serializable {

  private int seq;
  private String parent;
  private String child;

  // Default constructor, equals, and hashCode methods
}

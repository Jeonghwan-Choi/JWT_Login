package com.techon.login.repository;

import com.techon.login.entity.MenuCode;
import com.techon.login.entity.MenuRole;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRoleRepository extends JpaRepository<MenuRole, Long> {

  boolean existsByParent_CodeAndChild_CodeAndRole_RoleId(String parentCode, String childCode, String roleId);
}

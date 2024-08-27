package com.techon.login.repository;

import com.techon.login.entity.MenuCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MenuCodeRepository extends CrudRepository<MenuCode, String> {

  @Query("SELECT mc FROM MenuCode mc JOIN MenuRole mr ON mc.code = mr.parent.code WHERE mr.role.roleId IN :roles")
  List<MenuCode> findMenusByRoles(List<String> roles);
}

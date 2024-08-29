package com.techon.login.controller;

import com.techon.login.dto.GrantRole;
import com.techon.login.entity.Authority;
import com.techon.login.entity.MenuCode;
import com.techon.login.entity.Role;
import com.techon.login.repository.RoleRepository;
import com.techon.login.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {

  private final RoleService roleService;

  @GetMapping("/list")
  public ResponseEntity<List<Role>> getRoleList(
//      @RequestHeader("Authorization") String token
  ) {
    return ResponseEntity.ok(roleService.getAll());
  }

  @PostMapping("/grant")
  public ResponseEntity<Authority> grantRole(@RequestBody GrantRole grantRole) throws Exception {
    return ResponseEntity.ok(roleService.insertAuthority(grantRole.getRole(), grantRole.getMemberId()));
  }
}

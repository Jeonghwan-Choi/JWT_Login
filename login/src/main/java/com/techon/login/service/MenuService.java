package com.techon.login.service;

import com.techon.login.common.JwtUtil;
import com.techon.login.entity.MenuCode;
import com.techon.login.repository.MenuCodeRepository;
import com.techon.login.repository.MenuRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final JwtUtil jwtUtil;
  private final MenuCodeRepository menuCodeRepository;
  private final MenuRoleRepository menuRoleRepository;

  /**
   * Fetch available menus based on user roles stored in the JWT token.
   */
  public List<MenuCode> getAvailableMenus(String accessToken) {
    // Extract roles from token
    List<String> roles = jwtUtil.extractRoles(accessToken);

    // Fetch available menus based on roles
    return menuCodeRepository.findMenusByRoles(roles);
  }

  /**
   * Check if the user has access to a specific menu.
   */
  public boolean hasAccessToMenu(String accessToken, String parentCode, String childCode) {
    // Extract roles from token
    List<String> roles = jwtUtil.extractRoles(accessToken);

    // Check access by iterating over roles
    for (String role : roles) {
      if (menuRoleRepository.existsByParent_CodeAndChild_CodeAndRole_RoleId(parentCode, childCode, role)) {
        return true;
      }
    }
    return false;
  }
}

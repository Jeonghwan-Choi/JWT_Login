package com.techon.login.controller;

import com.techon.login.entity.MenuCode;
import com.techon.login.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

  private final MenuService menuService;

  /**
   * Endpoint to fetch available menus for the user based on their token.
   */
  @GetMapping("/available")
  public ResponseEntity<List<MenuCode>> getAvailableMenus(@RequestHeader("Authorization") String token) {
    String accessToken = token.replace("Bearer ", "");
    List<MenuCode> menus = menuService.getAvailableMenus(accessToken);
    return ResponseEntity.ok(menus);
  }

  /**
   * Endpoint to check if the user has access to a specific menu.
   */
  @GetMapping("/access")
  public ResponseEntity<Boolean> hasAccessToMenu(@RequestHeader("Authorization") String token,
      @RequestParam("parentCode") String parentCode,
      @RequestParam("childCode") String childCode) {
    String accessToken = token.replace("Bearer ", "");
    boolean hasAccess = menuService.hasAccessToMenu(accessToken, parentCode, childCode);
    return ResponseEntity.ok(hasAccess);
  }
}

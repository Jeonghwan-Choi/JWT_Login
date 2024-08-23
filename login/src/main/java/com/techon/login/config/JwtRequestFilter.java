package com.techon.login.config;

import com.techon.login.common.JwtUtil;
import com.techon.login.config.exception.InvalidTokenException;
import com.techon.login.config.exception.UserNotFoundException;
import com.techon.login.entity.Member;
import com.techon.login.service.MemberService;
import com.techon.login.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
  private final MemberService memberService;
  private final TokenService tokenInfoService;
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String token = authorizationHeader.substring(7);
      log.debug("###TOKEN : {}", token);

      try {
        Long userId = validateTokenAndExtractUserId(token);
        setUserAuthentication(userId, request);
      } catch (InvalidTokenException | UserNotFoundException e) {
        log.error("Authentication error: {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        return;
      } catch (Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private Long validateTokenAndExtractUserId(String token) {
    Long userId = jwtUtil.extractUserSeq(token);
    if (!jwtUtil.validateToken(token, userId) || tokenInfoService.findByAccessToken(token).isEmpty()) {
      throw new InvalidTokenException("Invalid token");
    }
    return userId;
  }

  private void setUserAuthentication(Long userId, HttpServletRequest request) {
    Optional<Member> memberOptional = memberService.findById(userId);
    if (memberOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }

    Member member = memberOptional.get();
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(
            member,
            null,
            member.getAuthorities() // Ensure getAuthorities() returns the correct roles
        );

    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

    // Add userId to request attribute
    request.setAttribute("userId", userId);
  }
//    private void setUserAuthentication(Long userId, HttpServletRequest request) {
//        Optional<UserInfo> userInfoOptional = userService.findById(userId);
//        if (userInfoOptional.isEmpty()) {
//            throw new UserNotFoundException("User not found");
//        }
//
//        UserInfo userInfo = userInfoOptional.get();
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                new UsernamePasswordAuthenticationToken(
//                        userInfo,
//                        null,
//                        userInfo.getRoles().stream()
//                                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//                                .collect(Collectors.toList())
//                );
//
//        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//        // Add userId to request attribute
//        request.setAttribute("userId", userId);
//    }
}

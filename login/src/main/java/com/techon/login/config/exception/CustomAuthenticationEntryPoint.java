package com.techon.login.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, IOException {
    Object exception = request.getAttribute("exception");

    if(exception instanceof ErrorCode){
      ErrorCode errorCode = (ErrorCode) exception;
      setResponse(response, errorCode);

      return;
    }

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }

  //한글 출력을 위해 getWriter() 사용 Spring을 거치는 게 아니어서 한글 처리를 위해선 꼭 해줘야함
  private void setResponse(HttpServletResponse response, ErrorCode exceptionCode) throws IOException {
    log.error("token error : {}, {}",exceptionCode.getCode(), exceptionCode.getMessage());

    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    JSONObject responseJson = new JSONObject();
    responseJson.put("message", exceptionCode.getMessage());
    responseJson.put("code", exceptionCode.getCode());

    response.getWriter().print(responseJson);
  }
}

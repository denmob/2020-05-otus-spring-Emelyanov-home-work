package ru.otus.hw13.security.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class AppAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest,
                     HttpServletResponse httpServletResponse,
                     AccessDeniedException e) throws IOException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      String message = String.format("User %s attempted to access the protected URL: %s", auth.getName(), httpServletRequest.getRequestURI());
      log.error(message);
    }
    httpServletResponse.sendRedirect( "/403");
  }
}

package ru.otus.hw12.error;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppControllerAdviceHandler {

  private static final String ERROR_TEMPLATE = "error/error";

  @ExceptionHandler(value = {NullPointerException.class})
  public ModelAndView handleNPE(Exception e, HttpServletRequest request) {
    return createModelAndView(
        request.getAttribute("javax.servlet.error.status_code").toString(),
        request.getAttribute("javax.servlet.error.request_uri").toString(),
        e.getMessage());
  }

  @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class, AccessDeniedException.class, NotFoundException.class})
  public ModelAndView handleWithRequest(HttpServletRequest request) {
    return createModelAndView(
        request.getAttribute("javax.servlet.error.status_code").toString(),
        request.getAttribute("javax.servlet.error.request_uri").toString(),
        request.getAttribute("javax.servlet.error.message").toString());
  }

  private ModelAndView createModelAndView(String code, String uri, String message) {
    ModelAndView modelAndView = new ModelAndView(ERROR_TEMPLATE);

    modelAndView.addObject("code", "Status code: " + code);
    modelAndView.addObject("uri", "Request uri: " + uri);
    modelAndView.addObject("message", "Message: " + message);
    return modelAndView;
  }
}

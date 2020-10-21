package ru.otus.web.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandleExceptionControllerAdvice {

  @ExceptionHandler(NullPointerException.class)
  public ModelAndView handleNPE(NullPointerException e) {
    ModelAndView modelAndView = new ModelAndView("pageGlobalError");
    modelAndView.addObject("message", e.getMessage());
    return modelAndView;
  }
}

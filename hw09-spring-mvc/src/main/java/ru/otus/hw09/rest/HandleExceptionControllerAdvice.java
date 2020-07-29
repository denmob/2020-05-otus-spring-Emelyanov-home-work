package ru.otus.hw09.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandleExceptionControllerAdvice {

  @ExceptionHandler(NullPointerException.class)
  public ModelAndView handleNPE(NullPointerException e) {
    ModelAndView modelAndView = new ModelAndView("error");
    modelAndView.addObject("message", e.getMessage());
    return modelAndView;
  }
}

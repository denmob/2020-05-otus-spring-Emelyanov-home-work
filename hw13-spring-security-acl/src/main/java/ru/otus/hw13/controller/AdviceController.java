package ru.otus.hw13.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdviceController {

  private static final String ERROR_TEMPLATE = "error/error";

  @ExceptionHandler(NullPointerException.class)
  public ModelAndView handleNPE(Exception e) {
    ModelAndView modelAndView = new ModelAndView(ERROR_TEMPLATE);
    modelAndView.addObject("message", "Message: " + e.getMessage());
    return modelAndView;
  }
}

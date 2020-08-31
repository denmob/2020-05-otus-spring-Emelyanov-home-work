package ru.otus.hw12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DefaultController {

  @GetMapping(value = {"/","/login"})
  public String login() {
    return "login";
  }

  @GetMapping(value = {"/error"})
  public String error() {
    return "error/error";
  }

  @GetMapping(value = {"/403"})
  public String error403() {
    return "error/403";
  }
}

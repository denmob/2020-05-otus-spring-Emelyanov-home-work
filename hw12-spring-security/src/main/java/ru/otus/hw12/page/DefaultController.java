package ru.otus.hw12.page;

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
}

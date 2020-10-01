package ru.otus.hw16.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DefaultPageController {

  @GetMapping(value = {"/error"})
  public String error() {
    return "error/error";
  }

  @GetMapping(value = {"/403"})
  public String error403() {
    return "error/403";
  }
}

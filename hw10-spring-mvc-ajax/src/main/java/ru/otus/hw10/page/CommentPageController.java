package ru.otus.hw10.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CommentPageController {

  @GetMapping("/page/comment/list")
  public String commentsPage() {
    return "page/comment/list";
  }
}

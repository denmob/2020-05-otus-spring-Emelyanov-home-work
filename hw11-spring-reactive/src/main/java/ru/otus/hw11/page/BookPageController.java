package ru.otus.hw11.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BookPageController {

  @GetMapping("/")
  public String root() {
    return "redirect:/pageBookList";
  }

  @GetMapping("/pageBookList")
  public String booksPage() {
    return "pageBookList";
  }

  @GetMapping("/pageBookCreate")
  public String pageBookCreate() {
    return "pageBookCreate";
  }

  @GetMapping("/pageBookEdit/{bookId}")
  public String pageBookEdit() {
    return "pageBookEdit";
  }
}

package ru.otus.web.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


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
  public String pageBookCreate(Model model) {
    model.addAttribute("authors", new ArrayList<>());
    model.addAttribute("genres", new ArrayList<>());
    return "pageBookCreate";
  }

  @GetMapping("/pageBookEdit/{bookId}")
  public String pageBookEdit(@PathVariable("bookId") String bookId, Model model) {
    model.addAttribute("authors", new ArrayList<>());
    model.addAttribute("genres", new ArrayList<>());
    return "pageBookEdit";
  }
}

package ru.otus.hw16.page;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw16.service.AuthorService;
import ru.otus.hw16.service.GenreService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookPageController {

  private final AuthorService authorService;
  private final GenreService genreService;

  @GetMapping("/")
  @SneakyThrows
  public String root() {
    return "redirect:/book/list";
  }

  @GetMapping("/book/list")
  public String booksPage() {
    return "book/list";
  }

  @GetMapping("/book/create")
  public String pageBookCreate(Model model) {
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "book/create";
  }

  @GetMapping("/book/edit/{bookId}")
  public String pageBookEdit(@PathVariable("bookId") String bookId, Model model) {
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "book/edit";
  }
}

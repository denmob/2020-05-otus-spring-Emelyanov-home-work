package ru.otus.hw16.page;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw16.service.AuthorService;
import ru.otus.hw16.service.GenreService;

@Controller
@RequiredArgsConstructor
@Timed("book")
public class BookPageController {

  private final AuthorService authorService;
  private final GenreService genreService;

  @GetMapping("/")
  @Timed(value = "book.redirect", longTask = true)
  public String root() {
    return "redirect:/book/list";
  }

  @GetMapping("/book/list")
  @Timed(value = "book.list", longTask = true)
  public String booksPage() {
    return "book/list";
  }

  @GetMapping("/book/create")
  @Timed(value = "book.create", longTask = true)
  public String pageBookCreate(Model model) {
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "book/create";
  }

  @GetMapping("/book/edit/{bookId}")
  @Timed(value = "book.edit", longTask = true)
  public String pageBookEdit(@PathVariable("bookId") String bookId, Model model) {
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "book/edit";
  }
}

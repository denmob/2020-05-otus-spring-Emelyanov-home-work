package ru.otus.hw10.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.service.AuthorService;
import ru.otus.hw10.service.GenreService;


@Controller
@RequiredArgsConstructor
public class BookPageController {

  private final AuthorService authorService;
  private final GenreService genreService;

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
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "pageBookCreate";
  }

  @GetMapping("/pageBookEdit/{bookId}")
  public String pageBookEdit(@PathVariable("bookId") String bookId,Model model) {
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "pageBookEdit";
  }
}

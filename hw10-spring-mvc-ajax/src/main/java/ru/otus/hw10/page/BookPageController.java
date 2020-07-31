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
  public String listBookPage() {
    return "listBook";
  }

  @GetMapping("/createBook")
  public String createBookPage(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "createBook";
  }

  @GetMapping("/editBook")
  public String editBookPage(Model model) {
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "editBook";
  }
}

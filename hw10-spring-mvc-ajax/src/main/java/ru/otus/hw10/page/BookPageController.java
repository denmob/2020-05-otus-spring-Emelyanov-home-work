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
    model.addAttribute("book", new Book());
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "pageBookCreate";
  }

  @GetMapping("/pageBookEdit")
  public String pageBookEdit(@RequestParam("bookId") String bookId,Model model) {
    model.addAttribute("book", Book.builder().id(bookId).build());
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "pageBookEdit";
  }
}

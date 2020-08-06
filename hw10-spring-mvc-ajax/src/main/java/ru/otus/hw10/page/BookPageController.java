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
  public String booksPage() {
    return "page/book/list";
  }

  @GetMapping("/page/book/create")
  public String pageBookCreate(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "page/book/create";
  }

  @GetMapping("/page/book/edit/{bookId}")
  public String pageBookEdit(@PathVariable("bookId") String bookId, Model model) {
    model.addAttribute("book", Book.builder().id(bookId).build());
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "page/book/edit";
  }
}

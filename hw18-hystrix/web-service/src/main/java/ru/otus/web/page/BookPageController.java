package ru.otus.web.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.model.Author;
import ru.otus.library.model.Genre;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;

@Controller
@RequiredArgsConstructor
public class BookPageController {

  private final RestService<Author> authorRestService = new RestServiceImpl<>();
  private final RestService<Genre> genreRestService = new RestServiceImpl<>();

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
    model.addAttribute("authors", authorRestService.getEntities("http://localhost:8002/api/author"));
    model.addAttribute("genres", genreRestService.getEntities("http://localhost:8003/api/genre"));
    return "pageBookCreate";
  }

  @GetMapping("/pageBookEdit/{bookId}")
  public String pageBookEdit(@PathVariable("bookId") String bookId, Model model) {
    model.addAttribute("authors", authorRestService.getEntities("http://localhost:8002/api/author"));
    model.addAttribute("genres", genreRestService.getEntities("http://localhost:8003/api/genre"));
    return "pageBookEdit";
  }
}

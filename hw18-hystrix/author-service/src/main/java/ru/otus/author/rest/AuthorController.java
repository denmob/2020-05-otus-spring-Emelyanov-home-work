package ru.otus.author.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.author.service.AuthorService;
import ru.otus.library.handler.NotFoundException;
import ru.otus.library.model.Author;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService authorService;

  @GetMapping("/api/author")
  public List<Author> getAuthors() {
    return authorService.findAll();
  }

  @GetMapping("/api/author/lastName")
  public Author getAuthor(@RequestParam(value = "lastName") String lastName) {
    return authorService.findByLastNameEquals(lastName).orElseThrow(() -> new NotFoundException("Not found entry author.lastName: " + lastName));
  }
}

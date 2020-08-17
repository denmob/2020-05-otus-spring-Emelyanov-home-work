package ru.otus.hw11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.repository.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorRepository authorRepository;

  @GetMapping(value = "/api/author", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<Author> getAuthors() {
    return authorRepository.findAll();
  }
}

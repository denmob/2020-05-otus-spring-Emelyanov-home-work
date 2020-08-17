package ru.otus.hw11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

  private final GenreRepository genreRepository;

  @GetMapping(value = "/api/genre", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<Genre> getGenres() {
    return genreRepository.findAll();
  }
}

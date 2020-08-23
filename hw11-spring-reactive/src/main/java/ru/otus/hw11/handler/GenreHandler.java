package ru.otus.hw11.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.repository.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class GenreHandler {

  private final GenreRepository genreRepository;

  public Mono<ServerResponse> listGenre() {
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(genreRepository.findAll(), Genre.class);
  }
}

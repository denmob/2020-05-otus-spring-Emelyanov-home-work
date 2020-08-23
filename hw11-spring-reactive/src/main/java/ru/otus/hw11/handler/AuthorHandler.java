package ru.otus.hw11.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.repository.AuthorRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class AuthorHandler {

  private final AuthorRepository authorRepository;

  public Mono<ServerResponse> listAuthor() {
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(authorRepository.findAll(), Author.class);
  }
}

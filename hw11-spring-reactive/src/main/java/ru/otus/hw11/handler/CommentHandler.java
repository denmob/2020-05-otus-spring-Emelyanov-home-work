package ru.otus.hw11.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.hw11.repository.CommentRepository;
import ru.otus.hw11.dto.CommentDto;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class CommentHandler {

  private final CommentRepository commentRepository;

  public Mono<ServerResponse> findAllByBookId(ServerRequest serverRequest) {
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(commentRepository.findAllByBookId(serverRequest.pathVariable("bookId")).map(CommentDto::toDto), CommentDto.class);
  }
}

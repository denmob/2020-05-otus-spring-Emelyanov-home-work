package ru.otus.hw11.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.hw11.repository.BookRepository;
import ru.otus.hw11.dto.BookDto;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class BookHandler {

  private final BookRepository bookRepository;

  public Mono<ServerResponse> getBook(ServerRequest serverRequest) {
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(bookRepository.findById(serverRequest.pathVariable("bookId")).map(BookDto::toDto), BookDto.class);
  }

  public Mono<ServerResponse> deleteBook(ServerRequest serverRequest) {
    return ServerResponse.ok()
        .body(bookRepository.deleteBookById(serverRequest.pathVariable("bookId")), Mono.class);
  }
}

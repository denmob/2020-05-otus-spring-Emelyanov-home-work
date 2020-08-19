package ru.otus.hw11.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.hw11.handler.AuthorHandler;
import ru.otus.hw11.handler.BookHandler;
import ru.otus.hw11.handler.CommentHandler;
import ru.otus.hw11.handler.GenreHandler;

import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterFunctionConfig {

  private final BookHandler bookHandler;
  private final AuthorHandler authorHandler;
  private final GenreHandler genreHandler;
  private final CommentHandler commentHandler;

  @Bean
  RouterFunction<ServerResponse> getBookByIdRoute() {
    return route(GET("/api/book/{bookId}"), serverRequest -> bookHandler.getBook(Objects.requireNonNull(serverRequest)));
  }

  @Bean
  RouterFunction<ServerResponse> deleteBookByIdRoute() {
    return route(DELETE("/api/book/{bookId}"), serverRequest -> bookHandler.deleteBook(Objects.requireNonNull(serverRequest)));
  }

  @Bean
  RouterFunction<ServerResponse> composedRoutes() {
    return route()
        .GET(("/api/author"), (ServerRequest request) -> authorHandler.listAuthor())
        .GET(("/api/genre"), (ServerRequest request) -> genreHandler.listGenre())
        .GET(("/api/comments/{bookId}"), (ServerRequest serverRequest) -> commentHandler.findAllByBookId(Objects.requireNonNull(serverRequest)))
        .build();
  }
}

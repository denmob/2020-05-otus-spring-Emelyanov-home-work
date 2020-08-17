package ru.otus.hw11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw11.rest.dto.CommentDto;
import ru.otus.hw11.service.CommentService;


@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping(value = "/api/comments/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Flux<CommentDto> getComments(@PathVariable("bookId") String bookId) {
    return commentService.readAllForBook(bookId).map(CommentDto::toDto);
  }
}

package ru.otus.hw16.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw16.rest.dto.CommentDto;
import ru.otus.hw16.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping(value = "/api/comment/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<CommentDto> getComments(@PathVariable("bookId") String bookId) {
    return commentService.readAllForBook(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
  }
}

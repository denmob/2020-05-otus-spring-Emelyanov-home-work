package ru.otus.comment.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ru.otus.comment.service.CommentService;
import ru.otus.library.model.dto.CommentDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping(value = "/api/comment/book/id", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CommentDto> getComments(@RequestParam(value = "bookId") String bookId) {
      return commentService.readAllForBook(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
  }
}

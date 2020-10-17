package ru.otus.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.model.dto.CommentDto;
import ru.otus.web.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping(value = "/api/comment/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CommentDto> getComments(@PathVariable("id") String id) {
    return commentService.getComments(id);
  }
}

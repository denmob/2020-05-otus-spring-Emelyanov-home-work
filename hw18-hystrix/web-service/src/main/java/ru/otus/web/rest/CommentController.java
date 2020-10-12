package ru.otus.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.model.dto.CommentDto;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

  @GetMapping(value = "/api/comment/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<CommentDto> getComments(@PathVariable("bookId") String bookId) {
    //  return commentService.readAllForBook(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
    return new ArrayList<>();
  }
}

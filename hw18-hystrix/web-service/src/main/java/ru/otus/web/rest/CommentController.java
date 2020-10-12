package ru.otus.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.model.dto.CommentDto;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final RestService<CommentDto> commentDtoRestService = new RestServiceImpl<>();

  @GetMapping(value = "/api/comment/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CommentDto> getComments(@PathVariable("bookId") String bookId) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("bookId", bookId);
    return commentDtoRestService.getEntities("http://localhost:8004/api/comment/book/id", multiValueMap);
  }
}

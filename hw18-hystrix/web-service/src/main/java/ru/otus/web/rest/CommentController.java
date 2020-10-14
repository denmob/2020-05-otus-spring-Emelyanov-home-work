package ru.otus.web.rest;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.dto.CommentDto;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;

import java.util.List;

@RestController
public class CommentController {

  private static final String URL_BOOK_SERVICE = "http://comment-service/api/comment/book";

  private final RestService<CommentDto> commentDtoRestService;

  public CommentController(RestTemplate restTemplateRibbon) {
    this.commentDtoRestService = new RestServiceImpl<>(restTemplateRibbon);
  }

  @GetMapping(value = "/api/comment/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CommentDto> getComments(@PathVariable("id") String id) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("id", id);
    return commentDtoRestService.getEntities(URL_BOOK_SERVICE, multiValueMap);
  }
}

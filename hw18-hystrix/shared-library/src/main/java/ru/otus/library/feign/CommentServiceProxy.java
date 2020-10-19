package ru.otus.library.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.dto.CommentDto;

import java.util.List;

@FeignClient(name = "comment-service-client",
    url = "http://localhost:8011",
    path = "/cs")
public interface CommentServiceProxy {

  @GetMapping(value = "/api/comment/book")
  List<CommentDto> getComments(@RequestParam("id") String id);
}

package ru.otus.library.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.Author;

@FeignClient(name = "author-service-client",
    url = "http://localhost:8011",
    path = "/as")
public interface AuthorServiceProxy {

  @GetMapping(value = "/api/author")
  Author getAuthorId(@RequestParam(value = "id") String id);

  @GetMapping(value = "/api/author")
  Author getAuthorByLastName(@RequestParam(value = "lastName") String lastName);
}

package ru.otus.library.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.Author;

import java.util.Date;


@FeignClient(name = "author-service-client",
    url = "http://localhost:8011",
    path = "/as",
    fallbackFactory = AuthorServiceProxyFallback.class,
    qualifier = "authorServiceProxy")
public interface AuthorServiceProxy {

  @GetMapping(value = "/api/author")
  Author getAuthorId(@RequestParam(value = "id") String id);

  @GetMapping(value = "/api/author")
  Author getAuthorByLastName(@RequestParam(value = "lastName") String lastName);
}

@Slf4j
@Component
class AuthorServiceProxyFallback implements AuthorServiceProxy {

  private final Author defaultAuthor = Author.builder()
      .firstName("defaultFirstName")
      .lastName("defaultLastName")
      .birthday(new Date(System.currentTimeMillis()))
      .build();

  @Override
  public Author getAuthorId(String id) {
    log.error("Invoke fallbackFactory getAuthorId: {}", id);
    return defaultAuthor;
  }

  @Override
  public Author getAuthorByLastName(String lastName) {
    log.error("Invoke fallbackFactory getAuthorByLastName: {}", lastName);
    return defaultAuthor;
  }
}


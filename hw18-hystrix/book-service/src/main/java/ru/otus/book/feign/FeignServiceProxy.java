package ru.otus.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.Author;
import ru.otus.library.model.Genre;

@FeignClient("zuul-service-gateway")
public interface FeignServiceProxy {

  @GetMapping(value = "/gs/api/genre")
  Genre getGenreByName(@RequestParam(value = "name") String name);

  @GetMapping(value = "/as/api/author")
  Author getAuthorByLastName(@RequestParam(value = "lastName") String lastName);
}

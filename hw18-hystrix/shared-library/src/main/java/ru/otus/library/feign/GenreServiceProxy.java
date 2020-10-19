package ru.otus.library.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.Genre;

@FeignClient( name = "genre-service-client",
    url = "http://localhost:8011",
    path = "/gs")
public interface GenreServiceProxy {

  @GetMapping(value = "/api/genre")
  Genre getGenreId(@RequestParam(value = "id") String id);

  @GetMapping(value = "/api/genre")
  Genre getGenreByName(@RequestParam(value = "name") String name);
}

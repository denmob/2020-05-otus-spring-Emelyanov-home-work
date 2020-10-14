package ru.otus.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.Genre;

@FeignClient(name = "genre-service")
@RequestMapping(value = "/api")
public interface GenreServiceProxy {

	@GetMapping(value = "/genre")
  Genre getGenreByName(@RequestParam(value = "name") String name);
}

package ru.otus.comment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.Book;

@FeignClient(name = "book-service")
@RequestMapping(value = "/api")
public interface BookServiceProxy {

	@GetMapping(value = "/book",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  Book getBookByTitle(@RequestParam(value = "title") String title);
}

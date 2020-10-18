package ru.otus.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.model.Author;
import ru.otus.library.model.Genre;
import ru.otus.library.model.dto.BookDto;
import ru.otus.library.model.dto.CommentDto;

import java.util.List;

@FeignClient("zuul-service-gateway")
public interface FeignServiceProxy {

  @GetMapping(value = "/gs/api/genre")
  Genre getGenreId(@RequestParam(value = "id") String id);

  @GetMapping(value = "/as/api/author")
  Author getAuthorId(@RequestParam(value = "id") String id);

  @GetMapping(value = "/cs/api/comment/book")
  List<CommentDto> getComments(@RequestParam("id") String id);

  @GetMapping(value = "/bs/api/book")
  List<BookDto> getBooks(@RequestParam(value = "countBook") String id);

  @GetMapping(value = "/bs/api/book")
  BookDto getBook(@RequestParam(value = "id") String id);

  @PostMapping(value = "/bs/api/book")
  BookDto saveBook(BookDto bookDto);

  @PutMapping(value = "/bs/api/book")
  BookDto editBook(BookDto bookDto);

  @DeleteMapping(value = "/bs/api/book")
  boolean deleteBook(@RequestParam(value = "id") String id);
}

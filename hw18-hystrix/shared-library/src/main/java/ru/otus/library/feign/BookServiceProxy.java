package ru.otus.library.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.model.Book;
import ru.otus.library.model.dto.BookDto;

import java.util.List;

@FeignClient( name = "book-service-client",
    url = "http://localhost:8011",
    path = "/bs")
public interface BookServiceProxy {

  @GetMapping(value = "/api/book")
  List<BookDto> getBooks(@RequestParam(value = "countBook") String id);

  @GetMapping(value = "/api/book")
  BookDto getBook(@RequestParam(value = "id") String id);

  @GetMapping(value = "/api/book",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  Book getBookByTitle(@RequestParam(value = "title") String title);

  @PostMapping(value = "/api/book")
  BookDto saveBook(BookDto bookDto);

  @PutMapping(value = "/api/book")
  BookDto editBook(BookDto bookDto);

  @DeleteMapping(value = "/api/book")
  boolean deleteBook(@RequestParam(value = "id") String id);
}

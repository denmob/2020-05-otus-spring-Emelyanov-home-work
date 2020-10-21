package ru.otus.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.model.dto.BookDto;
import ru.otus.web.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("/api/book")
  public List<BookDto> getBooks() {
    return bookService.getBooks("5");
  }

  @GetMapping("/api/book/{id}")
  public BookDto getBook(@PathVariable("id") String id) {
    return bookService.getBook(id);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BookDto saveBook(@RequestBody BookDto bookDto) {
    return bookService.saveBook(bookDto);
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto editBook(@RequestBody BookDto bookDto) {
    return bookService.editBook(bookDto);
  }

  @DeleteMapping("/api/book/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable("id") String id) {
    if (bookService.deleteBook(id))
      return ResponseEntity.ok().build();
    else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}

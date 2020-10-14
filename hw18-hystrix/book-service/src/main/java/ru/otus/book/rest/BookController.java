package ru.otus.book.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.book.service.BookService;
import ru.otus.library.handler.NotFoundException;
import ru.otus.library.model.Book;
import ru.otus.library.model.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping(value = "/api/book", params = "countBook", consumes = MediaType.APPLICATION_JSON_VALUE)
  public List<BookDto> getBooks(@RequestParam(value = "countBook", defaultValue = "5") Integer countBook) {
    return bookService.getLastAddedBooks(countBook).stream().map(BookDto::toDto).collect(Collectors.toList());
  }

  @GetMapping(value = "/api/book", params = "title", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto getBookTitle(@RequestParam(value = "title") String title) {
    Book book = bookService.readBookByTitle(title).orElseThrow(() -> new NotFoundException("Not found entry book.title: " + title));
    return BookDto.toDto(book);
  }

  @GetMapping(value = "/api/book", params = "id", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto getBookId(@RequestParam(value = "id") String id) {
    Book book = bookService.readBookById(id).orElseThrow(() -> new NotFoundException("Not found entry book.id: " + id));
    return BookDto.toDto(book);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BookDto post(@RequestBody BookDto bookDto) {
    return BookDto.toDto(bookService.save(BookDto.toBook(bookDto)));
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto put(@RequestBody BookDto bookDto) {
    return BookDto.toDto(bookService.save(BookDto.toBook(bookDto)));
  }

  @DeleteMapping(value = "/api/book", params = "id", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@RequestParam("id") String id) {
    bookService.deleteBookById(id);
    return ResponseEntity.ok().build();
  }

}

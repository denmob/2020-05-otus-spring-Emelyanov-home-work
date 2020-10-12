package ru.otus.book.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.otus.book.service.BookService;
import ru.otus.library.handler.NotFoundException;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.model.dto.BookDto;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public List<BookDto> getBooks(@RequestParam(value = "countBook", defaultValue = "5") int countBook) {
    return bookService.getLastAddedBooks(countBook).stream().map(BookDto::toDto).collect(Collectors.toList());
  }

  @GetMapping(value = "/api/book/title", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto getBookTitle(@RequestParam(value = "bookTitle") String bookTitle) {
    Book book = bookService.readBookByTitle(bookTitle).orElseThrow(() -> new NotFoundException("Not found entry book.title: " + bookTitle));
    return BookDto.toDto(book);
  }

  @GetMapping(value = "/api/book/id", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto getBookId(@RequestParam(value = "bookId") String bookId) {
    Book book = bookService.readBookById(bookId).orElseThrow(() -> new NotFoundException("Not found entry book.id: " + bookId));
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

  @DeleteMapping("/api/book/id")
  public ResponseEntity<Void> delete(@RequestParam("bookId") String bookId) {
    bookService.deleteBookById(bookId);
    return ResponseEntity.ok().build();
  }

}

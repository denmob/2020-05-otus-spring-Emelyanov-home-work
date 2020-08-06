package ru.otus.hw10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.rest.dto.BookDto;
import ru.otus.hw10.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("/api/book/list")
  @ResponseStatus(HttpStatus.OK)
  public List<BookDto> getBooks() {
    return bookService.getLastAddedBooks(5).stream().map(BookDto::toDto).collect(Collectors.toList());
  }

  @GetMapping("/api/book/edit/{bookId}")
  public BookDto edit(@PathVariable("bookId") String bookId) {
    Book book = bookService.readBookById(bookId).orElseThrow(NotFoundException::new);
    return BookDto.toDto(book);
  }

  @PostMapping("/api/book/save")
  public BookDto save(@RequestBody BookDto bookDto) {
    bookService.save(BookDto.toBook(bookDto));
    return bookDto;
  }

  @DeleteMapping("/api/book/delete/{bookId}")
  public void delete(@PathVariable("bookId") String bookId) {
    bookService.deleteBookById(bookId);
  }
}

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

  @GetMapping("/api/book")
  public List<BookDto> getBooks(@RequestParam(value = "countBook", defaultValue = "5") int countBook) {
    return bookService.getLastAddedBooks(countBook).stream().map(BookDto::toDto).collect(Collectors.toList());
  }

  @GetMapping("/api/book/title")
  public BookDto getBook(@RequestParam(value = "bookTitle") String bookTitle) {
    Book book = bookService.readBookByTitle(bookTitle).orElseThrow(() -> new NotFoundException("Not found entry book.title: " + bookTitle));
    return BookDto.toDto(book);
  }

  @GetMapping("/api/book/{bookId}")
  public BookDto edit(@PathVariable("bookId") String bookId) {
    Book book = bookService.readBookById(bookId).orElseThrow(() -> new NotFoundException("Not found entry book.id: " + bookId));
    return BookDto.toDto(book);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BookDto post(@RequestBody BookDto bookDto) {
    return BookDto.toDto(bookService.save(buildBookFromDto(bookDto)));
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto put(@RequestBody BookDto bookDto) {
    return BookDto.toDto(bookService.save(buildBookFromDto(bookDto)));
  }

  @DeleteMapping("/api/book/{bookId}")
  public ResponseEntity<Void> delete(@PathVariable("bookId") String bookId) {
    bookService.deleteBookById(bookId);
    return ResponseEntity.ok().build();
  }

  private Book buildBookFromDto(BookDto bookDto) {
//    return Book.builder()
//        .id(bookDto.getId())
//        .title(bookDto.getTitle())
//        .date(bookDto.getDate())
//        .author(authorService.findById(bookDto.getAuthor().getId()).orElseThrow(NotFoundException::new))
//        .genre(genreService.findById(bookDto.getGenre().getId()).orElseThrow(NotFoundException::new))
//        .build();
    return new Book();
  }
}

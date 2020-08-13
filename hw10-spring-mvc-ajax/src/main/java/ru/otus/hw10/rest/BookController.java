package ru.otus.hw10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.rest.dto.BookDto;
import ru.otus.hw10.service.AuthorService;
import ru.otus.hw10.service.BookService;
import ru.otus.hw10.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  @GetMapping("/api/books")
  public List<BookDto> getBooks(@RequestParam(value = "countBook", defaultValue = "5") int countBook) {
    return bookService.getLastAddedBooks(countBook).stream().map(BookDto::toDto).collect(Collectors.toList());
  }

  @GetMapping("/api/book/{bookId}")
  public BookDto edit(@PathVariable("bookId") String bookId) {
    Book book = bookService.readBookById(bookId).orElseThrow(NotFoundException::new);
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
    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(authorService.findById(bookDto.getAuthor().getId()).orElseThrow(NotFoundException::new))
        .genre(genreService.findById(bookDto.getGenre().getId()).orElseThrow(NotFoundException::new))
        .build();
  }
}

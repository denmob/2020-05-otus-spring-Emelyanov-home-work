package ru.otus.hw11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.repository.AuthorRepository;
import ru.otus.hw11.repository.BookRepository;
import ru.otus.hw11.repository.GenreRepository;
import ru.otus.hw11.rest.dto.BookDto;
import ru.otus.hw11.service.AuthorService;
import ru.otus.hw11.service.BookService;
import ru.otus.hw11.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookRepository bookService;
  private final AuthorRepository authorService;
  private final GenreRepository genreService;

  @GetMapping("/api/book")
  public Flux<BookDto> getBooks(@RequestParam(value = "countBook", defaultValue = "5") int countBook) {
    // to do
    // countBook
    return bookService.findAll().map(BookDto::toDto);
  }

  @GetMapping("/api/book/{bookId}")
  public Mono<BookDto> edit(@PathVariable("bookId") String bookId) {
    return bookService.findById(bookId).map(BookDto::toDto);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Mono<BookDto> post(@RequestBody BookDto bookDto) {
    return bookService.save(buildBookFromDto(bookDto)).map(BookDto::toDto);
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<BookDto> put(@RequestBody BookDto bookDto) {
    return bookService.save(buildBookFromDto(bookDto)).map(BookDto::toDto);
  }

  @DeleteMapping("/api/book/{bookId}")
  public Mono<Void> delete(@PathVariable("bookId") String bookId) {
    return bookService.deleteBookById(bookId);
  }

  private Book buildBookFromDto(BookDto bookDto) {
    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(authorService.findById(bookDto.getAuthor().getId()).block())
        .genre(genreService.findById(bookDto.getGenre().getId()).block())
        .build();
  }
}

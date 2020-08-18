package ru.otus.hw11.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.repository.AuthorRepository;
import ru.otus.hw11.repository.BookRepository;
import ru.otus.hw11.repository.GenreRepository;
import ru.otus.hw11.rest.dto.BookDto;

import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookRepository bookService;
  private final AuthorRepository authorService;
  private final GenreRepository genreService;

  @GetMapping("/api/book")
  public Flux<BookDto> getBooks(@RequestParam(value = "page", defaultValue = "0") long page,
                                @RequestParam(value = "size", defaultValue = "5") long size) {
    return  bookService
        .findAll()
        .sort(Comparator.comparing(Book::getId).reversed())
        .skip(page * size)
        .take(size)
        .map(BookDto::toDto);
  }

  @GetMapping("/api/book/{bookId}")
  public Mono<BookDto> edit(@PathVariable("bookId") String bookId) {
    return bookService.findById(bookId).map(BookDto::toDto);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Mono<BookDto> post(@RequestBody BookDto bookDto) {
    return saveBook(bookDto);
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<BookDto> put(@RequestBody BookDto bookDto) {
    return saveBook(bookDto);
  }

  @DeleteMapping("/api/book/{bookId}")
  public Mono<Void> delete(@PathVariable("bookId") String bookId) {
    return bookService.deleteBookById(bookId);
  }

  private Mono<BookDto> saveBook(BookDto bookDto) {
    return Mono.zip(authorService.findById(bookDto.getAuthor().getId()), genreService.findById(bookDto.getGenre().getId()))
        .flatMap(data -> {
          data.getT1();
          data.getT2();
          return Mono.just(buildBookFromDto(bookDto, data.getT1(), data.getT2()));
        })
        .flatMap(bookService::save)
        .map(BookDto::toDto);
  }

  private Book buildBookFromDto(BookDto bookDto, Author author, Genre genre) {
    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(author)
        .genre(genre)
        .build();
  }
}

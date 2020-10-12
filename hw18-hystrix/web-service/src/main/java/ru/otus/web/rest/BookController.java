package ru.otus.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;
import ru.otus.library.model.dto.BookDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final RestService<BookDto> dtoRestService = new RestServiceImpl<>();
  private final RestService<Author> authorRestService = new RestServiceImpl<>();
  private final RestService<Genre> genreRestService = new RestServiceImpl<>();

  @GetMapping("/api/book")
  public List<BookDto> getBooks() {
    return dtoRestService.getEntities("http://localhost:8001/api/book");
  }

  @GetMapping("/api/book/{bookId}")
  public BookDto edit(@PathVariable("bookId") String bookId) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("bookId", bookId);
    return dtoRestService.getEntity("http://localhost:8001/api/book/id", multiValueMap, BookDto.class);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BookDto post(@RequestBody BookDto bookDto) {
    return dtoRestService.postEntity("http://localhost:8001/api/book", buildBookFromDto(bookDto), BookDto.class);
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto put(@RequestBody BookDto bookDto) {
    return dtoRestService.putEntity("http://localhost:8001/api/book", buildBookFromDto(bookDto), BookDto.class);
  }

  @DeleteMapping("/api/book/{bookId}")
  public ResponseEntity<Void> delete(@PathVariable("bookId") String bookId) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("bookId", bookId);
    dtoRestService.deleteEntity("http://localhost:8001/api/book/id", multiValueMap, BookDto.class);
    return ResponseEntity.ok().build();
  }

  private Book buildBookFromDto(BookDto bookDto) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("authorId", bookDto.getAuthor().getId());
    Author author = authorRestService.getEntity("http://localhost:8002/api/author/id", multiValueMap, Author.class);
    multiValueMap.add("genreId", bookDto.getGenre().getId());
    Genre genre = genreRestService.getEntity("http://localhost:8003/api/genre/id", multiValueMap, Genre.class);

    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(author)
        .genre(genre)
        .build();
  }
}

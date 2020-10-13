package ru.otus.web.rest;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;
import ru.otus.library.model.dto.BookDto;

import java.util.List;

@RestController
public class BookController {

  private final RestService<BookDto> bookDtoRestService;
  private final RestService<Author> authorRestService = new RestServiceImpl<>();
  private final RestService<Genre> genreRestService = new RestServiceImpl<>();

  public BookController(RestTemplate restTemplateRibbon) {
    this.bookDtoRestService = new RestServiceImpl<>(restTemplateRibbon);
  }

  @GetMapping("/api/book")
  public List<BookDto> getBooks() {
    return bookDtoRestService.getEntities("http://book-service/api/book");
  }

  @GetMapping("/api/book/{bookId}")
  public BookDto edit(@PathVariable("bookId") String bookId) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("bookId", bookId);
    return bookDtoRestService.getEntity("http://book-service/api/book/id", multiValueMap, BookDto.class);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BookDto post(@RequestBody BookDto bookDto) {
    return bookDtoRestService.postEntity("http://book-service/api/book", buildBookFromDto(bookDto), BookDto.class);
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto put(@RequestBody BookDto bookDto) {
    return bookDtoRestService.putEntity("http://book-service/api/book", buildBookFromDto(bookDto), BookDto.class);
  }

  @DeleteMapping("/api/book/{bookId}")
  public ResponseEntity<Void> delete(@PathVariable("bookId") String bookId) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("bookId", bookId);
    bookDtoRestService.deleteEntity("http://book-service/api/book/id", multiValueMap, BookDto.class);
    return ResponseEntity.ok().build();
  }

  private Book buildBookFromDto(BookDto bookDto) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("authorId", bookDto.getAuthor().getId());
    Author author = authorRestService.getEntity("http://localhost:8001/api/author/id", multiValueMap, Author.class);
    multiValueMap.add("genreId", bookDto.getGenre().getId());
    Genre genre = genreRestService.getEntity("http://localhost:8002/api/genre/id", multiValueMap, Genre.class);

    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(author)
        .genre(genre)
        .build();
  }
}

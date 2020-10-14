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

  private static final String URL_BOOK_SERVICE = "http://book-service/api/book";
  private final RestService<BookDto> bookDtoRestService;
  private final RestService<Author> authorRestService = new RestServiceImpl<>();
  private final RestService<Genre> genreRestService = new RestServiceImpl<>();

  public BookController(RestTemplate restTemplateRibbon) {
    this.bookDtoRestService = new RestServiceImpl<>(restTemplateRibbon);
  }

  @GetMapping("/api/book")
  public List<BookDto> getBooks() {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("countBook", "10");
    return bookDtoRestService.getEntities(URL_BOOK_SERVICE, multiValueMap);
  }

  @GetMapping("/api/book/{id}")
  public BookDto edit(@PathVariable("id") String id) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("id", id);
    return bookDtoRestService.getEntity(URL_BOOK_SERVICE, multiValueMap, BookDto.class);
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BookDto post(@RequestBody BookDto bookDto) {
    return bookDtoRestService.postEntity(URL_BOOK_SERVICE, buildBookFromDto(bookDto), BookDto.class);
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto put(@RequestBody BookDto bookDto) {
    return bookDtoRestService.putEntity(URL_BOOK_SERVICE, buildBookFromDto(bookDto), BookDto.class);
  }

  @DeleteMapping("/api/book/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") String id) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("id", id);
    if (bookDtoRestService.deleteEntity(URL_BOOK_SERVICE, multiValueMap, BookDto.class)) {
      return ResponseEntity.ok().build();
    } else return ResponseEntity.notFound().build();
  }

  private Book buildBookFromDto(BookDto bookDto) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("id", bookDto.getAuthor().getId());
    Author author = authorRestService.getEntity("http://localhost:8001/api/author", multiValueMap, Author.class);
    multiValueMap.set("id", bookDto.getGenre().getId());
    Genre genre = genreRestService.getEntity("http://localhost:8002/api/genre", multiValueMap, Genre.class);

    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(author)
        .genre(genre)
        .build();
  }
}

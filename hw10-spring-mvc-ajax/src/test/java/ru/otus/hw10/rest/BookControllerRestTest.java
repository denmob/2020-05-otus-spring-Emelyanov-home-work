package ru.otus.hw10.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.hw10.model.Author;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.model.Genre;
import ru.otus.hw10.rest.dto.BookDto;
import ru.otus.hw10.service.*;

import java.util.*;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class})
@SpringBootTest(classes = {BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class, BookController.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerRestTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @MockBean
  private BookServiceImpl bookService;

  @MockBean
  private AuthorServiceImpl authorService;

  @MockBean
  private GenreServiceImpl genreService;

  private static HttpEntity<?> httpEntity;

  private Book book;
  private final HttpHeaders httpHeaders = new HttpHeaders();
  private final Author author = new Author("0", "FirstName", "LastName", now());
  private final Genre genre = new Genre("0", "newGenre");

  @BeforeEach
  void beforeEach() {
    book = Book.builder().id("123").title("title").author(author).genre(genre).build();
    httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpEntity = new HttpEntity<>(httpHeaders);
  }


  @Test
  @SneakyThrows
  void getBooks() {
    int countBook = 1;

    List<Book> books = new ArrayList<>();
    books.add(book);
    Page<Book> bookPage = new PageImpl<>(books);
    when(bookService.getLastAddedBooks(countBook)).thenReturn(bookPage);

    UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/api/books").queryParam("countBook", countBook);

    ResponseEntity<String> responseEntity = testRestTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, httpEntity, String.class);

    Book actualBook = JsonPath.parse(responseEntity.getBody()).read("$[0]", Book.class);
    assertEquals(book.getTitle(), actualBook.getTitle());
    assertEquals(book.getId(), actualBook.getId());

    verify(bookService, times(1)).getLastAddedBooks(countBook);
  }

  @Test
  @SneakyThrows
  void edit() {
    when(bookService.readBookById(book.getId())).thenReturn(Optional.ofNullable(book));

    Map<String, String> urlParams = new HashMap<>();
    urlParams.put("bookId", book.getId());

    ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/book/{bookId}", HttpMethod.GET, httpEntity, String.class, urlParams);

    BookDto actualBook = new ObjectMapper().readValue(responseEntity.getBody(), BookDto.class);
    assertEquals(book.getTitle(), actualBook.getTitle());
    assertEquals(book.getId(), actualBook.getId());

    verify(bookService, times(1)).readBookById(book.getId());
  }

  @Test
  @SneakyThrows
  void save() {
    BookDto bookDto = BookDto.toDto(book);

    when(authorService.findById(bookDto.getAuthor().getId())).thenReturn(Optional.ofNullable(bookDto.getAuthor()));
    when(genreService.findById(bookDto.getGenre().getId())).thenReturn(Optional.ofNullable(bookDto.getGenre()));
    when(bookService.save(book)).thenReturn(book);

    String bookDtoJson = new ObjectMapper().writeValueAsString(bookDto);
    httpEntity = new HttpEntity<>(bookDtoJson, httpHeaders);

    ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/book", HttpMethod.POST, httpEntity, String.class);
    BookDto actualBook = new ObjectMapper().readValue(responseEntity.getBody(), BookDto.class);
    assertEquals(book.getTitle(), actualBook.getTitle());
    assertEquals(book.getId(), actualBook.getId());

    verify(authorService, times(1)).findById(bookDto.getAuthor().getId());
    verify(genreService, times(1)).findById(bookDto.getGenre().getId());
    verify(bookService, times(1)).save(book);
  }

  @Test
  void delete() {
    when(bookService.deleteBookById(book.getId())).thenReturn(true);

    Map<String, String> urlParams = new HashMap<>();
    urlParams.put("bookId", book.getId());

    ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/book/{bookId}", HttpMethod.DELETE, httpEntity, String.class, urlParams);

    verify(bookService, times(1)).deleteBookById(book.getId());
  }
}

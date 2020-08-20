package ru.otus.hw11.rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.config.RouterFunctionConfig;
import ru.otus.hw11.handler.AuthorHandler;
import ru.otus.hw11.handler.BookHandler;
import ru.otus.hw11.handler.CommentHandler;
import ru.otus.hw11.handler.GenreHandler;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Comment;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.repository.AuthorRepository;
import ru.otus.hw11.repository.BookRepository;
import ru.otus.hw11.repository.CommentRepository;
import ru.otus.hw11.repository.GenreRepository;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@WebFluxTest
@ContextConfiguration(classes = {RouterFunctionConfig.class,
    BookHandler.class, AuthorHandler.class, GenreHandler.class, CommentHandler.class,
    BookRepository.class, AuthorRepository.class, GenreRepository.class, CommentRepository.class})
class RouterFunctionConfigTest {

  @Autowired
  private ApplicationContext context;

  @MockBean
  private BookRepository bookRepository;

  @MockBean
  private AuthorRepository authorRepository;

  @MockBean
  private GenreRepository genreRepository;

  @MockBean
  private CommentRepository commentRepository;

  private WebTestClient webTestClient;

  @BeforeEach
  void beforeEach() {
    webTestClient = WebTestClient.bindToApplicationContext(context).build();
  }

  @Test
  @DisplayName("get /api/book/{bookId}")
  void getBookByIdRoute() {
    Book book = Book.builder().id("2").title("new book").build();
    Mono<Book> bookMono = Mono.just(book);
    when(bookRepository.findById(book.getId())).thenReturn(bookMono);

    webTestClient.get()
        .uri("/api/book/" + book.getId())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Book.class)
        .value(bookResponse -> {
              assertThat(bookResponse.getId()).isEqualTo(book.getId());
              assertThat(bookResponse.getTitle()).isEqualTo(book.getTitle());
            }
        );
  }

  @Test
  @DisplayName("get /api/author")
  void getAuthor() {
    Author author1 = Author.builder().firstName("firstName1").lastName("").birthday(new Date()).build();
    Author author2 = Author.builder().firstName("firstName2").lastName("").birthday(new Date()).build();

    when(authorRepository.findAll()).thenReturn(Flux.just(author1, author2));

    webTestClient.get()
        .uri("/api/author")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Author.class)
        .value(authorResponse -> {
              assertThat(authorResponse.get(0).getFirstName()).isEqualTo(author1.getFirstName());
              assertThat(authorResponse.get(1).getFirstName()).isEqualTo(author2.getFirstName());
            }
        );
  }

  @Test
  @DisplayName("get /api/genre")
  void getGenre() {
    Genre genre1 = Genre.builder().name("genre1").build();
    Genre genre2 = Genre.builder().name("genre2").build();

    when(genreRepository.findAll()).thenReturn(Flux.just(genre1, genre2));

    webTestClient.get()
        .uri("/api/genre")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Genre.class)
        .value(genreResponse -> {
              assertThat(genreResponse.get(0).getName()).isEqualTo(genre1.getName());
              assertThat(genreResponse.get(1).getName()).isEqualTo(genre2.getName());
            }
        );
  }

  @Test
  @DisplayName("get /api/comment/book/{bookId}")
  void getCommentByBookIdRoute() {
    Book book = Book.builder().id("2").title("new book").build();
    Comment comment1 = Comment.builder().commentary("comment1").build();
    Comment comment2 = Comment.builder().commentary("comment2").build();
    when(commentRepository.findAllByBookId(book.getId())).thenReturn(Flux.just(comment1, comment2));

    webTestClient.get()
        .uri("/api/comment/book/" + book.getId())
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Comment.class)
        .value(commentResponse -> {
              assertThat(commentResponse.get(0).getCommentary()).isEqualTo(comment1.getCommentary());
              assertThat(commentResponse.get(1).getCommentary()).isEqualTo(comment2.getCommentary());
            }
        );
  }

  @Test
  @DisplayName("delete /api/book/{bookId}")
  void deleteBookByIdRoute() {
    Book book = Book.builder().id("2").title("new book").build();
    Mono<Long> longMono = Mono.just(100L);
    when(bookRepository.deleteBookById(book.getId())).thenReturn(longMono);

    EntityExchangeResult<byte[]> entityExchangeResult = webTestClient.delete()
        .uri("/api/book/" + book.getId())
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .returnResult();

    assertNotNull(entityExchangeResult.getResponseBodyContent());
    assertTrue(new String(entityExchangeResult.getResponseBodyContent()).contains("100"));
  }

  @Test
  @DisplayName("put /api/book with result 404")
  void putBook() {
    Book book = Book.builder().id("2").title("new book").build();
    Mono<Book> bookMono = Mono.just(book);
    when(bookRepository.save(book)).thenReturn(bookMono);

    webTestClient.put()
        .uri("/api/book")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(book), Book.class)
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}

package ru.otus.hw10.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw10.model.Author;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.model.Genre;
import ru.otus.hw10.rest.dto.BookDto;
import ru.otus.hw10.service.AuthorServiceImpl;
import ru.otus.hw10.service.BookServiceImpl;
import ru.otus.hw10.service.GenreServiceImpl;

import java.util.*;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Import({BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class, BookController.class})
@WebMvcTest(controllers = BookController.class)
class BookControllerMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookServiceImpl bookService;

  @MockBean
  private AuthorServiceImpl authorService;

  @MockBean
  private GenreServiceImpl genreService;

  private Book book;
  private final Author author = new Author("0", "FirstName", "LastName", now());
  private final Genre genre = new Genre("0", "newGenre");

  @BeforeEach
  void beforeEach() {
    book = Book.builder().id("123").title("title").author(author).genre(genre).build();
  }

  @Test
  @SneakyThrows
  void getBooks() {
    int countBook = 1;

    List<Book> books = new ArrayList<>();
    books.add(book);
    Page<Book> bookPage = new PageImpl<>(books);
    when(bookService.getLastAddedBooks(countBook)).thenReturn(bookPage);

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/books").param("countBook", String.valueOf(countBook))).andExpect(status().isOk()).andReturn();

    Book actualBook = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$[0]", Book.class);
    assertEquals(book.getTitle(), actualBook.getTitle());
    assertEquals(book.getId(), actualBook.getId());

    verify(bookService, times(1)).getLastAddedBooks(countBook);
  }

  @Test
  @SneakyThrows
  void edit() {
    when(bookService.readBookById(book.getId())).thenReturn(Optional.ofNullable(book));

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/book/{bookId}", book.getId())).andExpect(status().isOk()).andReturn();

    BookDto actualBook = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BookDto.class);
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

    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/book").contentType(MediaType.APPLICATION_JSON).content(bookDtoJson)).andExpect(status().isOk()).andReturn();

    BookDto actualBook = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), BookDto.class);
    assertEquals(book.getTitle(), actualBook.getTitle());
    assertEquals(book.getId(), actualBook.getId());

    verify(authorService, times(1)).findById(bookDto.getAuthor().getId());
    verify(genreService, times(1)).findById(bookDto.getGenre().getId());
    verify(bookService, times(1)).save(book);
  }

  @Test
  @SneakyThrows
  void delete() {
    when(bookService.deleteBookById(book.getId())).thenReturn(true);

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/{bookId}", book.getId())).andExpect(status().isOk()).andReturn();

    verify(bookService, times(1)).deleteBookById(book.getId());
  }

  @SpringBootConfiguration
  public static class StopWebMvcScan {
  }
}

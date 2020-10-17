package ru.otus.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Genre;
import ru.otus.library.model.dto.BookDto;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;
import ru.otus.web.feign.FeignServiceProxy;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

  private static final String URL_BOOK_SERVICE = "http://book-service/api/book";
  private static final String URL_AUTHOR_SERVICE = "http://author-service/api/author";
  private static final String URL_GENRE_SERVICE = "http://genre-service/api/genre";

  private final RestService<BookDto> bookDtoRestService;
  private final RestService<Author> authorRestService;
  private final RestService<Genre> genreRestService;
  private final FeignServiceProxy feignServiceProxy;

  public BookServiceImpl(RestTemplate restTemplateRibbon, FeignServiceProxy feignServiceProxy) {
    this.bookDtoRestService = new RestServiceImpl<>(restTemplateRibbon);
    this.authorRestService = new RestServiceImpl<>(restTemplateRibbon);
    this.genreRestService = new RestServiceImpl<>(restTemplateRibbon);
    this.feignServiceProxy = feignServiceProxy;
  }

  @Override
  @HystrixCommand(commandKey = "getBooks", fallbackMethod = "getBooksFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public List<BookDto> getBooks(String countBook) {
    return feignServiceProxy.getBooks(countBook);
  }

  @Override
  @HystrixCommand(commandKey = "getBook", fallbackMethod = "getBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public BookDto getBook(String id) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("id", id);
    return bookDtoRestService.getEntity(URL_BOOK_SERVICE, queryParams, BookDto.class);
  }

  @Override
  @HystrixCommand(commandKey = "saveBook", fallbackMethod = "saveBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public BookDto saveBook(BookDto bookDto) {
    return bookDtoRestService.postEntity(URL_BOOK_SERVICE, buildBookFromDto(bookDto), BookDto.class);
  }

  @Override
  @HystrixCommand(commandKey = "editBook", fallbackMethod = "editBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public BookDto editBook(BookDto bookDto) {
    return bookDtoRestService.putEntity(URL_BOOK_SERVICE, buildBookFromDto(bookDto), BookDto.class);
  }

  @Override
  @HystrixCommand(commandKey = "deleteBook", fallbackMethod = "deleteBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public boolean deleteBook(String id) {
    return feignServiceProxy.deleteBook(id);
  }

  @SuppressWarnings("unused")
  private List<BookDto> getBooksFallbackMethod(String countBook) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("countBook", countBook);
    return bookDtoRestService.getEntities(URL_BOOK_SERVICE, queryParams);
  }

  @SuppressWarnings("unused")
  private BookDto getBookFallbackMethod(String id) {
    return feignServiceProxy.getBook(id);
  }

  @SuppressWarnings("unused")
  private BookDto saveBookFallbackMethod(BookDto bookDto) {
    return feignServiceProxy.saveBook(bookDto);
  }

  @SuppressWarnings("unused")
  private BookDto editBookFallbackMethod(BookDto bookDto) {
    return feignServiceProxy.editBook(bookDto);
  }

  @SuppressWarnings("unused")
  private boolean deleteBookFallbackMethod(String id) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("id", id);
    return bookDtoRestService.deleteEntity(URL_BOOK_SERVICE, queryParams, BookDto.class);
  }

  private Book buildBookFromDto(BookDto bookDto) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("id", bookDto.getAuthor().getId());
    Author author = authorRestService.getEntity(URL_AUTHOR_SERVICE, queryParams, Author.class);
    queryParams.set("id", bookDto.getGenre().getId());
    Genre genre = genreRestService.getEntity(URL_GENRE_SERVICE, queryParams, Genre.class);

    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(author)
        .genre(genre)
        .build();
  }
}

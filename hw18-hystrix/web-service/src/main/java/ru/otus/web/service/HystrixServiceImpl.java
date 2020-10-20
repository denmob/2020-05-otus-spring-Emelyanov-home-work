package ru.otus.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.library.feign.BookServiceProxy;
import ru.otus.library.model.dto.BookDto;
import ru.otus.library.service.RestTemplateRibbonBook;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HystrixServiceImpl implements HystrixService {

  @Value("${app.url.book-service}")
  private String urlBookService;

  private final RestTemplateRibbonBook restTemplateRibbonBook;
  private final BookServiceProxy bookServiceProxy;

  @Override
  @HystrixCommand(commandKey = "getBooks", fallbackMethod = "getBooksFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public List<BookDto> getBooks(String countBook) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("countBook", countBook);
    return restTemplateRibbonBook.getEntities(urlBookService, queryParams);
  }

  @Override
  @HystrixCommand(commandKey = "getBook", fallbackMethod = "getBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public BookDto getBook(String id) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("id", id);
    return restTemplateRibbonBook.getEntity(urlBookService, queryParams, BookDto.class);
  }

  @Override
  @HystrixCommand(commandKey = "saveBook", fallbackMethod = "saveBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public BookDto saveBook(BookDto bookDto) {
    return restTemplateRibbonBook.postEntity(urlBookService, bookDto, BookDto.class);
  }

  @Override
  @HystrixCommand(commandKey = "editBook", fallbackMethod = "editBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public BookDto editBook(BookDto bookDto) {
    return restTemplateRibbonBook.putEntity(urlBookService, bookDto, BookDto.class);
  }

  @Override
  @HystrixCommand(commandKey = "deleteBook", fallbackMethod = "deleteBookFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public boolean deleteBook(String id) {
    return bookServiceProxy.deleteBook(id);
  }

  @SuppressWarnings("unused")
  private List<BookDto> getBooksFallbackMethod(String countBook) {
    return bookServiceProxy.getBooks(countBook);
  }

  @SuppressWarnings("unused")
  private BookDto getBookFallbackMethod(String id) {
    return bookServiceProxy.getBook(id);
  }

  @SuppressWarnings("unused")
  private BookDto saveBookFallbackMethod(BookDto bookDto) {
    return bookServiceProxy.saveBook(bookDto);
  }

  @SuppressWarnings("unused")
  private BookDto editBookFallbackMethod(BookDto bookDto) {
    return bookServiceProxy.editBook(bookDto);
  }

  @SuppressWarnings("unused")
  private boolean deleteBookFallbackMethod(String id) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("id", id);
    return restTemplateRibbonBook.deleteEntity(urlBookService, queryParams, BookDto.class);
  }
}

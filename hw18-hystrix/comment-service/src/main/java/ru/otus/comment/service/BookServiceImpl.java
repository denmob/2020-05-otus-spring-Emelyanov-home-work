package ru.otus.comment.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.comment.feign.BookServiceProxy;
import ru.otus.library.model.Book;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final DefaultBookService defaultBookService;
  private final BookServiceProxy bookServiceProxy;

  @Override
  @HystrixCommand(commandKey = "getBookByTitle", fallbackMethod = "getBookDefaultBookService", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public Book getBookByTitle(String title) {
    log.info("Invoke getBookByTitle");
    return bookServiceProxy.getBookByTitle(title);
  }

  @SuppressWarnings("unused")
  public Book getBookDefaultBookService(String title) {
    log.error("Invoke getBookDefaultBookService");
    return defaultBookService.getBookByTitle(title);
  }
}

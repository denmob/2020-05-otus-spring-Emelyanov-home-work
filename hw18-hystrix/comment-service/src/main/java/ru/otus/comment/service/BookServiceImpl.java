package ru.otus.comment.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
  @HystrixCommand(fallbackMethod = "getBookDefaultBookService")
  public Book getBookByTitle(String title) {
    return bookServiceProxy.getBookByTitle(title);
  }

  public Book getBookDefaultBookService(String title) {
    log.error("Invoke getBookDefaultBookService");
    return defaultBookService.getBookByTitle(title);
  }
}

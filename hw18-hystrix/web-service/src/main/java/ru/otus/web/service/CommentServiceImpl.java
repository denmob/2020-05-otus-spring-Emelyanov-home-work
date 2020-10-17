package ru.otus.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.dto.CommentDto;
import ru.otus.library.service.RestService;
import ru.otus.library.service.RestServiceImpl;
import ru.otus.web.feign.FeignServiceProxy;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

  private static final String URL_BOOK_SERVICE = "http://comment-service/api/comment/book";

  private final RestService<CommentDto> commentDtoRestService;
  private final FeignServiceProxy feignServiceProxy;

  public CommentServiceImpl(RestTemplate restTemplateRibbon, FeignServiceProxy feignServiceProxy) {
    this.commentDtoRestService = new RestServiceImpl<>(restTemplateRibbon);
    this.feignServiceProxy = feignServiceProxy;
  }

  @Override
  @HystrixCommand(commandKey = "getComments", fallbackMethod = "getCommentsFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public List<CommentDto> getComments(String id) {
    return feignServiceProxy.getComments(id);
  }

  @SuppressWarnings("unused")
  private List<CommentDto> getCommentsFallbackMethod(String id) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("id", id);
    return commentDtoRestService.getEntities(URL_BOOK_SERVICE, multiValueMap);
  }
}

package ru.otus.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.library.model.Comment;
import ru.otus.library.model.dto.AuthorDto;
import ru.otus.library.model.dto.CommentDto;
import ru.otus.library.feign.CommentServiceProxy;
import ru.otus.library.service.RestTemplateRibbonComment;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  @Value("${app.url.comment-service}")
  private String urlCommentService;

  private final RestTemplateRibbonComment restTemplateRibbonComment;
  private final CommentServiceProxy feignCommentProxy;

  @Override
  @HystrixCommand(commandKey = "getComments", fallbackMethod = "getCommentsFallbackMethod", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public List<Comment> getComments(String id) {
    return feignCommentProxy.getComments(id).stream().map(CommentDto::toComment).collect(Collectors.toList());
  }

  @SuppressWarnings("unused")
  private List<Comment> getCommentsFallbackMethod(String id) {
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("id", id);
    return restTemplateRibbonComment.getEntities(urlCommentService, multiValueMap).stream().map(CommentDto::toComment).collect(Collectors.toList());
  }
}

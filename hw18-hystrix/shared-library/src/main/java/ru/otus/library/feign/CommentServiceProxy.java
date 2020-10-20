package ru.otus.library.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.model.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "comment-service-client",
    url = "http://localhost:8011",
    path = "/cs",
    fallback = CommentServiceProxyFallback.class,
    qualifier = "commentServiceProxy")
public interface CommentServiceProxy {
  @GetMapping(value = "/api/comment/book")
  List<CommentDto> getComments(@RequestParam("id") String id);
}

@Slf4j
@Component
class CommentServiceProxyFallback implements CommentServiceProxy {

  @Override
  public List<CommentDto> getComments(String id) {
    log.error("Invoke fallbackFactory getComments: {}", id);
    List<CommentDto> comments = new ArrayList<>();
    comments.add(CommentDto.builder().commentary("defaultComment1").build());
    comments.add(CommentDto.builder().commentary("defaultComment2").build());
    return comments;
  }
}

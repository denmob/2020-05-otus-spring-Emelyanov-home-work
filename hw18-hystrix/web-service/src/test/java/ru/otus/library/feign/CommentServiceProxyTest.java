package ru.otus.library.feign;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.library.model.dto.CommentDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class})
@SpringBootTest(classes = {CommentServiceProxy.class, CommentServiceProxyFallback.class}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CommentServiceProxyTest {

  @Autowired
  public CommentServiceProxy commentServiceProxy;

  @Test
  void getComments() {
    List<CommentDto> commentDtoList = commentServiceProxy.getComments("1");
    assertThat(commentDtoList).isNotEmpty().hasSize(2);
    assertEquals("defaultComment1",commentDtoList.get(0).getCommentary());
    assertEquals("defaultComment2",commentDtoList.get(1).getCommentary());
  }
}

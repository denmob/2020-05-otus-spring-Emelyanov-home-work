package ru.otus.library.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.library.model.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class, MongoAutoConfiguration.class})
@SpringBootTest(classes = {AuthorServiceProxy.class, AuthorServiceProxyFallback.class},
    webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AuthorServiceProxyTest {

  @Autowired
  public AuthorServiceProxy authorServiceProxy;

  @Test
  void getAuthorId() {
    Author author = authorServiceProxy.getAuthorId("1");
    assertEquals("defaultFirstName", author.getFirstName());
  }

  @Test
  void getAuthorByLastName() {
    Author author = authorServiceProxy.getAuthorByLastName("name");
    assertEquals("defaultLastName", author.getLastName());
  }
}

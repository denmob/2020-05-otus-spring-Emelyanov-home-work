package ru.otus.hw17.service;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw17.config.MongoConfig;
import ru.otus.hw17.repository.BookRepository;
import ru.otus.hw17.rest.projections.CustomBookEntity;

import static org.junit.jupiter.api.Assertions.*;

@EnableMongock
@EnableAutoConfiguration
@ActiveProfiles("test")
@EnableConfigurationProperties
@EnableMongoRepositories(basePackageClasses = BookRepository.class)
@SpringBootTest(classes = {RestServiceImpl.class, MongoConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RestServiceImplTest {

  @Autowired
  private RestServiceImpl<CustomBookEntity> customBookEntityRestService;

  @Test
  void getObject_CustomBookEntity() {
    String url = "http://localhost:8080/rest/book/search/find-by-author-lastName?authorLastName=Bloch";
    CustomBookEntity expect = customBookEntityRestService.getObject(url, CustomBookEntity.class);
    assertNotNull(expect);
    assertEquals("Bloch",expect.getAuthor().getLastName());
  }

  @Test
  @DisplayName("fail: returned non unique result")
  void getObject_null() {
    String url = "http://localhost:8080/rest/book/search/find-by-author-lastName?authorLastName=Langr";
    CustomBookEntity expect = customBookEntityRestService.getObject(url, CustomBookEntity.class);
    assertNull(expect);
  }
}

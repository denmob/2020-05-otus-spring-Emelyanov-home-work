package ru.otus.hw08.core.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.impl.config.MongoConfig;
import ru.otus.hw08.impl.config.MongockConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Slf4j
@DataMongoTest
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ComponentScan({"ru.otus.hw08.impl.config", "ru.otus.hw08.core.repositories"})
class AuthorRepositoryTest {

  @Autowired
  private AuthorRepository authorRepository;

  @Test
  void findByLastNameEquals() {
    Author author = authorRepository.findByLastNameEquals("Jeff(test)");
    log.info(author.toString());
  }

  @Test
  void deleteAuthorByLastNameEquals() {
  }
}

package ru.otus.hw08.config;

import com.github.cloudyrock.mongock.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongockConfig {

  @Bean
  public Mongock mongock(MongoTemplate mongoTemplate, @Value("${spring.mongock.changelogs}") String changelog) {
    return new SpringMongockBuilder(mongoTemplate, changelog).build();
  }
}

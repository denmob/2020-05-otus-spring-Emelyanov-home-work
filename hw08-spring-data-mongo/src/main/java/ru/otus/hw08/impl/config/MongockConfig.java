package ru.otus.hw08.impl.config;

import com.github.cloudyrock.mongock.*;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongockConfig {

  private static final String CHANGELOGS_PACKAGE = "ru.otus.hw08.impl.changelogs";

  @Bean
  public Mongock mongock(MongoConfig mongoConfig, MongoClient mongoClient) {
    return new SpringMongockBuilder(mongoClient, mongoConfig.getDatabase(), CHANGELOGS_PACKAGE).build();
  }
}

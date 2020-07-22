package ru.otus.hw08.impl.config;

import com.github.cloudyrock.mongock.*;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongockConfig {

  @Bean
  public Mongock mongock(MongoConfig mongoConfig, MongoClient mongoClient, @Value("${spring.mongock.changelogs}") String changelog) {
    return new SpringMongockBuilder(mongoClient, mongoConfig.getDatabase(), changelog).build();
  }
}

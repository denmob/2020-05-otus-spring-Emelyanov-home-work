package ru.otus.hw08.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Data;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.mongodb.MongoClient.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Data
@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongoConfig {

  private int port;
  private String database;
  private String uri;

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create(createMongoClientSettings());
  }

  @Bean
  public MongoDatabase mongoDatabase(MongoClient mongoClient) {
    return mongoClient.getDatabase(database);
  }

  private MongoClientSettings createMongoClientSettings() {
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    return MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();
  }
}

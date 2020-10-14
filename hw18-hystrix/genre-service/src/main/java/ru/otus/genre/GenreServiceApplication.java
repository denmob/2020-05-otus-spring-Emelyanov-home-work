package ru.otus.genre;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties
public class GenreServiceApplication {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(GenreServiceApplication.class, args);
  }
}


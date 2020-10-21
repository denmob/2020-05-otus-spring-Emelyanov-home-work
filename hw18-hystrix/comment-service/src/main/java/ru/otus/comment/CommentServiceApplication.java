package ru.otus.comment;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.library.SharedLibrary;

@EnableMongock
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
@EnableConfigurationProperties
@Import(SharedLibrary.class)
@EnableFeignClients(basePackages = {"ru.otus.library.feign"})
public class CommentServiceApplication {

  @SneakyThrows
  public static void main(String[] args) {
    SpringApplication.run(CommentServiceApplication.class, args);
  }
}


package ru.otus.git.cloud.config;

import lombok.Data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Data
@Slf4j
@Configuration
@ConfigurationProperties("spring.cloud.git.server.git")
public class GitConfig {

  private String uri;
  private String searchPaths;
  private String username;
  private String password;

  @Value("${spring.cloud.config.server.native.search-locations}")
  private String searchLocations;

  @Profile("native")
  @Bean
  public void printQasConfig() {
    log.info("Git git Primary(native)");
    log.info("search-locations: {}", searchLocations);
  }

  @Profile("local")
  @Bean
  public void printProdConfig() {
    log.info("Git git Local");
    printConfig();
  }

  @Profile("dev")
  @Bean
  public void printDevConfig() {
    log.info("Git git DEV");
    printConfig();
  }

  private void printConfig() {
    log.info("uri: {}", uri);
    log.info("search-paths: {}", searchPaths);
    log.info("username: {}", username);
  }
}

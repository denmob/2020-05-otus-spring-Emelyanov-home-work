package ru.otus.web.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateRibbonConfig {

  @LoadBalanced
  @Bean
  public RestTemplate restTemplateRibbon() {
    return new RestTemplate();
  }
}

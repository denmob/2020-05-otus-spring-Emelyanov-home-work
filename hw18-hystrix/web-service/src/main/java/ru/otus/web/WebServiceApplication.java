package ru.otus.web;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.otus.library.SharedLibrary;

@EnableHystrix
@EnableEurekaClient
@EnableCircuitBreaker
@EnableDiscoveryClient

@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients(basePackages = {"ru.otus.library.feign"})
@Import(SharedLibrary.class)
public class WebServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebServiceApplication.class, args);
  }
}


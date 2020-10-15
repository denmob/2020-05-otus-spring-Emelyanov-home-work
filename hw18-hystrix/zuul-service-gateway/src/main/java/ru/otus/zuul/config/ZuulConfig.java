package ru.otus.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.zuul.filters.ErrorFilter;
import ru.otus.zuul.filters.PostFilter;
import ru.otus.zuul.filters.PreFilter;
import ru.otus.zuul.filters.RouteFilter;

@Configuration
public class ZuulConfig {

  @Bean
  public PreFilter preFilter() {
    return new PreFilter();
  }
  @Bean
  public PostFilter postFilter() {
    return new PostFilter();
  }
  @Bean
  public ErrorFilter errorFilter() {
    return new ErrorFilter();
  }
  @Bean
  public RouteFilter routeFilter() {
    return new RouteFilter();
  }
}

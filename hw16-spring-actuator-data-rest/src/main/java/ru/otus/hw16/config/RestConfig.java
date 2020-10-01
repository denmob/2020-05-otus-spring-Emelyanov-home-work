package ru.otus.hw16.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import ru.otus.hw16.rest.projections.CustomBook;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration) {
    repositoryRestConfiguration.getProjectionConfiguration().addProjection(CustomBook.class);
  }
}

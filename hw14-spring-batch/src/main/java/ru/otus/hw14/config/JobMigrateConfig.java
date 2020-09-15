package ru.otus.hw14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobMigrateConfig {

  private final JobBuilderFactory jobBuilderFactory;
  private final Step migrateAuthorStep;
  private final Step migrateGenreStep;
  private final Step migrateBookStep;
  private final Step migrateCommentStep;

  @Bean
  public Job migrateDataJob() {
    return jobBuilderFactory.get("migrateDataJob")
        .start(migrateAuthorStep)
        .next(migrateGenreStep)
        .next(migrateBookStep)
        .next(migrateCommentStep)
        .build();
  }
}

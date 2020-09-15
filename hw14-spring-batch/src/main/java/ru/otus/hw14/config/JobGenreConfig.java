package ru.otus.hw14.config;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw14.model.document.Genre;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobGenreConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final DataSource dataSource;

  @Bean
  @StepScope
  public MongoItemReader<Genre> mongoItemGenreReader() {
    return new MongoItemReaderBuilder<Genre>()
        .name("mongoItemGenreReader")
        .template(mongoTemplate)
        .targetType(Genre.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<Genre, Genre> itemGenreProcessor() {
    return genre -> genre;
  }

  @Bean
  @StepScope
  public ItemWriter<Genre> jdbcItemGenreWriter() {
    JdbcBatchItemWriter<Genre> writer = new JdbcBatchItemWriter<>();
    writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
    writer.setSql("INSERT INTO genres (name) VALUES (:name)");
    writer.setDataSource(dataSource);
    return writer;
  }

  @Bean
  public Step migrateGenreStep() {
    return stepBuilderFactory.get("migrateGenreStep")
        .<Genre, Genre>chunk(CHUNK_SIZE)
        .reader(mongoItemGenreReader())
        .processor(itemGenreProcessor())
        .writer(jdbcItemGenreWriter())
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Job migrateGenreJob() {
    return jobBuilderFactory.get("migrateGenreJob")
        .start(migrateGenreStep())
        .build();
  }
}

package ru.otus.hw14.config;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
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
import ru.otus.hw14.model.document.Author;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepAuthorConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final DataSource dataSource;

  @Bean
  @StepScope
  public MongoItemReader<Author> mongoItemAuthorReader() {
    return new MongoItemReaderBuilder<Author>()
        .name("mongoItemAuthorReader")
        .template(mongoTemplate)
        .targetType(Author.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<Author, Author> itemAuthorProcessor() {
    return author -> author;
  }

  @Bean
  @StepScope
  public ItemWriter<Author> jdbcItemAuthorWriter() {
    JdbcBatchItemWriter<Author> writer = new JdbcBatchItemWriter<>();
    writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

    writer.setSql("INSERT INTO authors " +
        "(first_name, last_name, birthday) " +
        "VALUES (:firstName, :lastName, :birthday)");
    writer.setDataSource(dataSource);
    return writer;
  }

  @Bean
  public Step migrateAuthorStep() {
    return stepBuilderFactory.get("migrateAuthorStep")
        .<Author, Author>chunk(CHUNK_SIZE)
        .reader(mongoItemAuthorReader())
        .processor(itemAuthorProcessor())
        .writer(jdbcItemAuthorWriter())
        .allowStartIfComplete(true)
        .build();
  }
}

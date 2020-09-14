package ru.otus.hw14.config;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw14.model.document.*;
import ru.otus.hw14.service.ItemBookProcessorService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepBookConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final ItemBookProcessorService itemBookProcessorService;

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  @StepScope
  public MongoItemReader<Book> mongoItemBookReader() {

    return new MongoItemReaderBuilder<Book>()
        .name("mongoItemAuthorReader")
        .template(mongoTemplate)
        .targetType(Book.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<ru.otus.hw14.model.document.Book, ru.otus.hw14.model.table.Book> itemBookProcessor() {
    return itemBookProcessorService::convertDocumentToEntity;
  }

  @Bean
  @StepScope
  public ItemWriter<ru.otus.hw14.model.table.Book> jpaItemBookWriter() {
    JpaItemWriter<ru.otus.hw14.model.table.Book> bookJpaItemWriter = new JpaItemWriter<>();
    bookJpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
    return bookJpaItemWriter;
  }

  @Bean
  public Step migrateBookStep() {
    return stepBuilderFactory.get("migrateBookStep")
        .<Book, ru.otus.hw14.model.table.Book>chunk(CHUNK_SIZE)
        .reader(mongoItemBookReader())
        .processor(itemBookProcessor())
        .writer(jpaItemBookWriter())
        .allowStartIfComplete(true)
        .build();
  }
}

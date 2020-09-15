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
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw14.model.document.*;
import ru.otus.hw14.model.entity.BookEntity;
import ru.otus.hw14.service.ItemBookProcessorService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobBookConfig {

  private static final int CHUNK_SIZE = 5;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final ItemBookProcessorService itemBookProcessorService;

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  @StepScope
  public MongoItemReader<BookDocument> mongoItemBookReader() {
    return new MongoItemReaderBuilder<BookDocument>()
        .name("mongoItemBookReader")
        .template(mongoTemplate)
        .targetType(BookDocument.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<BookDocument, BookEntity> itemBookProcessor() {
    return itemBookProcessorService::convertDocumentToEntity;
  }

  @Bean
  @StepScope
  public ItemWriter<BookEntity> jpaItemBookWriter() {
    JpaItemWriter<BookEntity> bookJpaItemWriter = new JpaItemWriter<>();
    bookJpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
    return bookJpaItemWriter;
  }

  @Bean
  public Step migrateBookStep() {
    return stepBuilderFactory.get("migrateBookStep")
        .<BookDocument, BookEntity>chunk(CHUNK_SIZE)
        .reader(mongoItemBookReader())
        .processor(itemBookProcessor())
        .writer(jpaItemBookWriter())
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Job migrateBookJob() {
    return jobBuilderFactory.get("migrateBookJob")
        .start(migrateBookStep())
        .build();
  }
}

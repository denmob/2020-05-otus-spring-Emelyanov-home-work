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
import ru.otus.hw14.model.document.Comment;
import ru.otus.hw14.service.ItemCommentProcessorService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobCommentConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final ItemCommentProcessorService itemCommentProcessorService;

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  @StepScope
  public MongoItemReader<Comment> mongoItemCommentReader() {
    return new MongoItemReaderBuilder<Comment>()
        .name("mongoItemCommentReader")
        .template(mongoTemplate)
        .targetType(Comment.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<Comment, ru.otus.hw14.model.table.Comment> itemCommentProcessor() {
    return itemCommentProcessorService::convertDocumentToEntity;
  }

  @Bean
  @StepScope
  public ItemWriter<ru.otus.hw14.model.table.Comment> jpaItemCommentWriter() {
    JpaItemWriter<ru.otus.hw14.model.table.Comment> commentJpaItemWriter = new JpaItemWriter<>();
    commentJpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
    return commentJpaItemWriter;
  }

  @Bean
  public Step migrateCommentStep() {
    return stepBuilderFactory.get("migrateCommentStep")
        .<Comment, ru.otus.hw14.model.table.Comment>chunk(CHUNK_SIZE)
        .reader(mongoItemCommentReader())
        .processor(itemCommentProcessor())
        .writer(jpaItemCommentWriter())
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Job migrateCommentJob() {
    return jobBuilderFactory.get("migrateCommentJob")
        .start(migrateCommentStep())
        .build();
  }
}

package ru.otus.hw14.config;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
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
import ru.otus.hw14.model.document.AuthorDocument;
import ru.otus.hw14.model.entity.AuthorEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobAuthorConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final MongoTemplate mongoTemplate;

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  @StepScope
  public MongoItemReader<AuthorDocument> mongoItemAuthorReader() {
    return new MongoItemReaderBuilder<AuthorDocument>()
        .name("mongoItemAuthorReader")
        .template(mongoTemplate)
        .targetType(AuthorDocument.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<AuthorDocument, AuthorEntity> itemAuthorProcessor() {
    return author -> AuthorEntity.builder()
        .firstName(author.getFirstName())
        .lastName(author.getLastName())
        .birthday(author.getBirthday())
        .build();
  }

  @Bean
  @StepScope
  public ItemWriter<AuthorEntity> jdbcItemAuthorWriter() {
    JpaItemWriter<AuthorEntity> authorEntityJpaItemWriter = new JpaItemWriter<>();
    authorEntityJpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
    return authorEntityJpaItemWriter;
  }

  @Bean
  public Step migrateAuthorStep() {
    return stepBuilderFactory.get("migrateAuthorStep")
        .<AuthorDocument, AuthorEntity>chunk(CHUNK_SIZE)
        .reader(mongoItemAuthorReader())
        .processor(itemAuthorProcessor())
        .writer(jdbcItemAuthorWriter())
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Job migrateAuthorJob() {
    return jobBuilderFactory.get("migrateAuthorJob")
        .start(migrateAuthorStep())
        .build();
  }
}

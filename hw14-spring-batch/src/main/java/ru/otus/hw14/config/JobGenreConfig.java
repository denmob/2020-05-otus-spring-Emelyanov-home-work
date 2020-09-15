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
import ru.otus.hw14.model.document.GenreDocument;
import ru.otus.hw14.model.entity.GenreEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobGenreConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final MongoTemplate mongoTemplate;

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  @StepScope
  public MongoItemReader<GenreDocument> mongoItemGenreReader() {
    return new MongoItemReaderBuilder<GenreDocument>()
        .name("mongoItemGenreReader")
        .template(mongoTemplate)
        .targetType(GenreDocument.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  @StepScope
  public ItemProcessor<GenreDocument, GenreEntity> itemGenreProcessor() {
    return genre -> GenreEntity.builder().name(genre.getName()).build();
  }

  @Bean
  @StepScope
  public ItemWriter<GenreEntity> jdbcItemGenreWriter() {
    JpaItemWriter<GenreEntity> genreEntityJpaItemWriter = new JpaItemWriter<>();
    genreEntityJpaItemWriter.setEntityManagerFactory(entityManager.getEntityManagerFactory());
    return genreEntityJpaItemWriter;
  }

  @Bean
  public Step migrateGenreStep() {
    return stepBuilderFactory.get("migrateGenreStep")
        .<GenreDocument, GenreEntity>chunk(CHUNK_SIZE)
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

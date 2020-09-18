package ru.otus.hw14.config;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw14.model.document.GenreDocument;
import ru.otus.hw14.model.entity.GenreEntity;
import ru.otus.hw14.repository.crud.GenreCrudRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobGenreConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final GenreCrudRepository genreCrudRepository;

  @Bean
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
  public ItemProcessor<GenreDocument, GenreEntity> itemGenreProcessor() {
    return genre -> GenreEntity.builder().name(genre.getName()).build();
  }

  @Bean
  public ItemWriter<GenreEntity> repositoryItemGenreWriter() {
    RepositoryItemWriter<GenreEntity> writer = new RepositoryItemWriter<>();
    writer.setMethodName("save");
    writer.setRepository(genreCrudRepository);
    return writer;
  }

  @Bean
  public Step migrateGenreStep() {
    return stepBuilderFactory.get("migrateGenreStep")
        .<GenreDocument, GenreEntity>chunk(CHUNK_SIZE)
        .reader(mongoItemGenreReader())
        .processor(itemGenreProcessor())
        .writer(repositoryItemGenreWriter())
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

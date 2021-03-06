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
import ru.otus.hw14.model.document.*;
import ru.otus.hw14.model.entity.BookEntity;
import ru.otus.hw14.repository.crud.BookCrudRepository;
import ru.otus.hw14.service.ItemBookProcessorService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobBookConfig {

  private static final int CHUNK_SIZE = 5;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final ItemBookProcessorService itemBookProcessorService;
  private final BookCrudRepository bookCrudRepository;

  @Bean
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
  public ItemProcessor<BookDocument, BookEntity> itemBookProcessor() {
    return itemBookProcessorService::convertDocumentToEntity;
  }

  @Bean
  public ItemWriter<BookEntity> repositoryItemBookWriter() {
    RepositoryItemWriter<BookEntity> writer = new RepositoryItemWriter<>();
    writer.setMethodName("save");
    writer.setRepository(bookCrudRepository);
    return writer;
  }

  @Bean
  public Step migrateBookStep() {
    return stepBuilderFactory.get("migrateBookStep")
        .<BookDocument, BookEntity>chunk(CHUNK_SIZE)
        .reader(mongoItemBookReader())
        .processor(itemBookProcessor())
        .writer(repositoryItemBookWriter())
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

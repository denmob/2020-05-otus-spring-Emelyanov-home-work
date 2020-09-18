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
import ru.otus.hw14.model.document.CommentDocument;
import ru.otus.hw14.model.entity.CommentEntity;
import ru.otus.hw14.repository.crud.CommentCrudRepository;
import ru.otus.hw14.service.ItemCommentProcessorService;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobCommentConfig {

  private static final int CHUNK_SIZE = 5;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final MongoTemplate mongoTemplate;
  private final ItemCommentProcessorService itemCommentProcessorService;
  private final CommentCrudRepository commentCrudRepository;

  @Bean
  public MongoItemReader<CommentDocument> mongoItemCommentReader() {
    return new MongoItemReaderBuilder<CommentDocument>()
        .name("mongoItemCommentReader")
        .template(mongoTemplate)
        .targetType(CommentDocument.class)
        .jsonQuery("{ }")
        .sorts(ImmutableMap.of("_id", Sort.Direction.ASC))
        .build();
  }

  @Bean
  public ItemProcessor<CommentDocument, CommentEntity> itemCommentProcessor() {
    return itemCommentProcessorService::convertDocumentToEntity;
  }

  @Bean
  public ItemWriter<CommentEntity> repositoryItemCommentWriter() {
    RepositoryItemWriter<CommentEntity> writer = new RepositoryItemWriter<>();
    writer.setMethodName("save");
    writer.setRepository(commentCrudRepository);
    return writer;
  }

  @Bean
  public Step migrateCommentStep() {
    return stepBuilderFactory.get("migrateCommentStep")
        .<CommentDocument, CommentEntity>chunk(CHUNK_SIZE)
        .reader(mongoItemCommentReader())
        .processor(itemCommentProcessor())
        .writer(repositoryItemCommentWriter())
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

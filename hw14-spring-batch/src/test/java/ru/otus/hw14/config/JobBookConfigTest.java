package ru.otus.hw14.config;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.otus.hw14.model.document.BookDocument;
import ru.otus.hw14.model.entity.BookEntity;
import ru.otus.hw14.repository.crud.BookCrudRepository;
import ru.otus.hw14.repository.mongo.BookMongoRepository;
import ru.otus.hw14.service.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@EnableMongock
@SpringBatchTest
@ActiveProfiles("test")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EntityScan("ru.otus.hw14.model.entity")
@EnableJpaRepositories(basePackageClasses = {BookCrudRepository.class})
@EnableMongoRepositories(basePackageClasses = {BookMongoRepository.class})
@SpringBootTest(classes = {JobBookConfig.class, BatchConfig.class, MongoConfig.class,
    BookMongoServiceImpl.class, BookCrudServiceImpl.class, ItemBookProcessorServiceImpl.class})
class JobBookConfigTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  @Autowired
  private JobRepositoryTestUtils jobRepositoryTestUtils;
  @Autowired
  private MongoItemReader<BookDocument> bookDocumentMongoItemReader;
  @Autowired
  private BookMongoServiceImpl bookMongoService;
  @Autowired
  private BookCrudServiceImpl bookCrudService;
  @Autowired
  private ItemProcessor<BookDocument, BookEntity> bookEntityItemProcessor;
  @MockBean
  private ItemBookProcessorServiceImpl itemBookProcessorService;
  @Mock
  private EntityManagerFactory entityManagerFactory;
  @Mock
  private EntityManager entityManager;

  @BeforeEach
  void clearMetaData() {
    MockitoAnnotations.initMocks(this);
    TransactionSynchronizationManager.bindResource(this.entityManagerFactory, new EntityManagerHolder(this.entityManager));
    jobRepositoryTestUtils.removeJobExecutions();
  }

  @Test
  @SneakyThrows
  void mongoItemBookReader() {
    assertEquals("Pragmatic Unit Testing in Java 8 with JUnit(test)", Objects.requireNonNull(bookDocumentMongoItemReader.read()).getTitle());
    assertEquals("Effective Java(test)", Objects.requireNonNull(bookDocumentMongoItemReader.read()).getTitle());
    assertEquals("Java Core Fundamentals(test)", Objects.requireNonNull(bookDocumentMongoItemReader.read()).getTitle());
  }

  @Test
  @SneakyThrows
  void itemBookProcessor() {
    List<BookDocument> bookDocuments = (List<BookDocument>) bookMongoService.findAll();
    List<BookEntity> bookEntities = (List<BookEntity>) bookCrudService.findAll();

    when(itemBookProcessorService.convertDocumentToEntity(bookDocuments.get(0))).thenReturn(bookEntities.get(0));

    assertEquals(bookEntities.get(0), bookEntityItemProcessor.process(bookDocuments.get(0)));

    verify(itemBookProcessorService, times(1)).convertDocumentToEntity(bookDocuments.get(0));
  }

  @Test
  @SneakyThrows
  void jpaItemBookWriter() {
    JpaItemWriter<BookEntity> itemWriter = new JpaItemWriterBuilder<BookEntity>()
        .entityManagerFactory(this.entityManagerFactory)
        .build();
    itemWriter.afterPropertiesSet();

    List<BookEntity> bookEntities = (List<BookEntity>) bookCrudService.findAll();

    itemWriter.write(bookEntities);
    verify(this.entityManager).merge(bookEntities.get(0));
    verify(this.entityManager).merge(bookEntities.get(1));
    verify(this.entityManager).merge(bookEntities.get(2));
  }

  @Test
  void migrateBookStep() {
    List<BookDocument> bookDocuments = (List<BookDocument>) bookMongoService.findAll();
    List<BookEntity> bookEntities = (List<BookEntity>) bookCrudService.findAll();

    when(itemBookProcessorService.convertDocumentToEntity(bookDocuments.get(0))).thenReturn(bookEntities.get(0));
    when(itemBookProcessorService.convertDocumentToEntity(bookDocuments.get(1))).thenReturn(bookEntities.get(1));

    JobExecution jobExecution = jobLauncherTestUtils.launchStep("migrateBookStep");
    Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

    assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
    assertThat(actualStepExecutions.size(), is(1));

    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getWriteCount(), is(2)));
    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getReadCount(), is(3)));
  }

  @Test
  @SneakyThrows
  void migrateBookJob() {
    List<BookDocument> bookDocuments = (List<BookDocument>) bookMongoService.findAll();
    List<BookEntity> bookEntities = (List<BookEntity>) bookCrudService.findAll();

    when(itemBookProcessorService.convertDocumentToEntity(bookDocuments.get(0))).thenReturn(bookEntities.get(0));

    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

    assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));

    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getWriteCount(), is(1)));
    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getReadCount(), is(3)));
  }
}

package ru.otus.hw14.config;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.otus.hw14.model.document.CommentDocument;
import ru.otus.hw14.model.entity.CommentEntity;
import ru.otus.hw14.repository.crud.CommentCrudRepository;
import ru.otus.hw14.repository.mongo.CommentMongoRepository;
import ru.otus.hw14.service.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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
@EnableJpaRepositories(basePackageClasses = {CommentCrudRepository.class})
@EnableMongoRepositories(basePackageClasses = {CommentMongoRepository.class})
@SpringBootTest(classes = {JobCommentConfig.class, BatchConfig.class, MongoConfig.class,
    CommentMongoServiceImpl.class, CommentCrudServiceImpl.class, ItemCommentProcessorServiceImpl.class})
class JobCommentConfigTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  @Autowired
  private JobRepositoryTestUtils jobRepositoryTestUtils;
  @Autowired
  private MongoItemReader<CommentDocument> commentDocumentMongoItemReader;
  @Autowired
  private CommentMongoServiceImpl commentMongoService;
  @Autowired
  private CommentCrudServiceImpl commentCrudService;
  @Autowired
  private ItemProcessor<CommentDocument, CommentEntity> itemCommentProcessor;
  @MockBean
  private ItemCommentProcessorServiceImpl itemCommentProcessorService;
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
  void mongoItemCommentReader() {
    assertEquals("addComments01", Objects.requireNonNull(commentDocumentMongoItemReader.read()).getCommentary());
    assertEquals("addComments02", Objects.requireNonNull(commentDocumentMongoItemReader.read()).getCommentary());
    assertEquals("addComments03", Objects.requireNonNull(commentDocumentMongoItemReader.read()).getCommentary());
    assertEquals("addComments04", Objects.requireNonNull(commentDocumentMongoItemReader.read()).getCommentary());
  }


  @Test
  @SneakyThrows
  void jpaItemCommentWriter() {
    JpaItemWriter<CommentEntity> itemWriter = new JpaItemWriterBuilder<CommentEntity>()
        .entityManagerFactory(this.entityManagerFactory)
        .build();
    itemWriter.afterPropertiesSet();

    List<CommentEntity> commentEntities = (List<CommentEntity>) commentCrudService.findAll();

    itemWriter.write(commentEntities);
    verify(this.entityManager).merge(commentEntities.get(0));
    verify(this.entityManager).merge(commentEntities.get(1));
    verify(this.entityManager).merge(commentEntities.get(3));
  }

  @Test
  @SneakyThrows
  void itemCommentProcessor() {
    List<CommentDocument> commentDocuments = (List<CommentDocument>) commentMongoService.findAll();
    List<CommentEntity> commentEntities = (List<CommentEntity>) commentCrudService.findAll();

    when(itemCommentProcessorService.convertDocumentToEntity(commentDocuments.get(0))).thenReturn(commentEntities.get(0));

    assertEquals(commentEntities.get(0), itemCommentProcessor.process(commentDocuments.get(0)));

    verify(itemCommentProcessorService, times(1)).convertDocumentToEntity(commentDocuments.get(0));
  }

  @Test
  void migrateCommentStep() {
    List<CommentDocument> commentDocuments = (List<CommentDocument>) commentMongoService.findAll();
    List<CommentEntity> commentEntities = (List<CommentEntity>) commentCrudService.findAll();

    when(itemCommentProcessorService.convertDocumentToEntity(commentDocuments.get(0))).thenReturn(commentEntities.get(0));
    when(itemCommentProcessorService.convertDocumentToEntity(commentDocuments.get(1))).thenReturn(commentEntities.get(1));

    JobExecution jobExecution = jobLauncherTestUtils.launchStep("migrateCommentStep");
    Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

    assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
    assertThat(actualStepExecutions.size(), is(1));

    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getWriteCount(), is(2)));
    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getReadCount(), is(4)));
  }

  @Test
  @SneakyThrows
  void migrateCommentJob() {
    List<CommentDocument> commentDocuments = (List<CommentDocument>) commentMongoService.findAll();
    List<CommentEntity> commentEntities = (List<CommentEntity>) commentCrudService.findAll();

    when(itemCommentProcessorService.convertDocumentToEntity(commentDocuments.get(0))).thenReturn(commentEntities.get(0));

    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

    assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));

    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getWriteCount(), is(1)));
    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getReadCount(), is(4)));
  }
}

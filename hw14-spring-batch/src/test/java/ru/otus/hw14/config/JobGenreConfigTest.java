package ru.otus.hw14.config;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.hw14.model.document.GenreDocument;
import ru.otus.hw14.model.entity.GenreEntity;
import ru.otus.hw14.repository.crud.GenreCrudRepository;
import ru.otus.hw14.repository.mongo.GenreMongoRepository;
import ru.otus.hw14.service.GenreCrudServiceImpl;
import ru.otus.hw14.service.GenreMongoServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@EnableMongock
@SpringBatchTest
@ActiveProfiles("test")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EntityScan("ru.otus.hw14.model.entity")
@EnableJpaRepositories(basePackageClasses = {GenreCrudRepository.class})
@EnableMongoRepositories(basePackageClasses = {GenreMongoRepository.class})
@SpringBootTest(classes = {JobGenreConfig.class, BatchConfig.class, MongoConfig.class, GenreMongoServiceImpl.class, GenreCrudServiceImpl.class})
@Import(GenreEntity.class)
class JobGenreConfigTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  @Autowired
  private JobRepositoryTestUtils jobRepositoryTestUtils;
  @Autowired
  private MongoItemReader<GenreDocument> mongoItemGenreReader;
  @Autowired
  private GenreMongoServiceImpl genreMongoService;
  @Autowired
  private GenreCrudServiceImpl genreCrudService;

  @BeforeEach
  void clearMetaData() {
    jobRepositoryTestUtils.removeJobExecutions();
  }

  @Test
  @SneakyThrows
  void mongoItemGenreReader() {
    assertNotNull(genreMongoService.findById(Objects.requireNonNull(mongoItemGenreReader.read()).getId()));
    assertNotNull(genreMongoService.findById(Objects.requireNonNull(mongoItemGenreReader.read()).getId()));
    assertNotNull(genreMongoService.findById(Objects.requireNonNull(mongoItemGenreReader.read()).getId()));
  }

  @Test
  @SneakyThrows
  void jdbcItemGenreWriter() {
    assertEquals(3, ((List<GenreEntity>) genreCrudService.findAll()).size());
    jobLauncherTestUtils.launchJob();
    assertEquals(6, ((List<GenreEntity>) genreCrudService.findAll()).size());
  }

  @Test
  void migrateGenreStep() {
    JobExecution jobExecution = jobLauncherTestUtils.launchStep("migrateGenreStep");
    Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

    assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));
    assertThat(actualStepExecutions.size(), is(1));
  }

  @Test
  @SneakyThrows
  void migrateGenreJob() {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

    assertThat(actualJobExitStatus.getExitCode(), is("COMPLETED"));

    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getWriteCount(), is(3)));
    actualStepExecutions.forEach(stepExecution -> assertThat(stepExecution.getReadCount(), is(3)));
  }
}

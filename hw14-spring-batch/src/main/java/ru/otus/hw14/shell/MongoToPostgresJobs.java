package ru.otus.hw14.shell;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MongoToPostgresJobs {

  private final JobLauncher jobLauncher;
  private final Job migrateAuthorJob;
  private final Job migrateGenreJob;

  @SneakyThrows
  @ShellMethod(value = "migrate authors from mongo to postgres", key = {"amtp"})
  public String migrateAuthors() {
    JobExecution execution = jobLauncher.run(migrateAuthorJob, new JobParameters());
    return execution.toString();
  }

  @SneakyThrows
  @ShellMethod(value = "migrate authors from mongo to postgres", key = {"gmtp"})
  public String migrateGenres() {
    JobExecution execution = jobLauncher.run(migrateGenreJob, new JobParameters());
    return execution.toString();
  }
}

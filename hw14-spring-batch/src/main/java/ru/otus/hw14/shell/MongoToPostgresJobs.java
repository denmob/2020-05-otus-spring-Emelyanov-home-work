package ru.otus.hw14.shell;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.service.AuthorCrudService;
import ru.otus.hw14.service.BookCrudService;
import ru.otus.hw14.service.CommentCrudService;
import ru.otus.hw14.service.GenreCrudService;

@ShellComponent
@RequiredArgsConstructor
public class MongoToPostgresJobs {

  private final JobLauncher jobLauncher;
  private final Job migrateDataJob;
  private final CommentCrudService commentCrudService;
  private final BookCrudService bookCrudService;
  private final GenreCrudService genreCrudService;
  private final AuthorCrudService authorCrudService;

  @SneakyThrows
  @ShellMethod(value = "migrate data from mongo to postgres", key = {"start"})
  public String migrateData() {
    JobExecution execution = jobLauncher.run(migrateDataJob, new JobParameters());
    return execution.toString();
  }

  @SneakyThrows
  @Transactional
  @ShellMethod(value = "delete postgres data and restart migrate", key = {"restart"})
  public String restartMigrateData() {
    commentCrudService.deleteAll();
    bookCrudService.deleteAll();
    genreCrudService.deleteAll();
    authorCrudService.deleteAll();
    return migrateData();
  }
}

package ru.otus.hw04.impl.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.InteractiveInterfaceService;
import ru.otus.hw04.core.service.QuestionsService;
import ru.otus.hw04.core.service.TestValidatorService;
import ru.otus.hw04.core.service.TestingService;
import ru.otus.hw04.impl.configs.YamlProps;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

  private final YamlProps yamlProps;
  private final MessageSource messageSource;
  private final QuestionsService questionsService;
  private final TestValidatorService testValidatorService;

  private Student student;
  private int countProcessedQuestion = 0;


  @ShellMethod(value = "Start command", key = {"g", "go"})
  public String start() {
    return createWelcomeMessage();
  }

  @ShellMethod(value = "Greeting command", key = {"n", "name"})
  public String greeting(String userName) {
    this.student = new Student(userName);
    return createHelloMessage(userName);
  }

  @ShellMethod(value = "Start testing", key = {"s", "start"})
  @ShellMethodAvailability(value = "!isStartedTesting")
  public void startTesting() {
    nextQuestion();
  }

  @ShellMethod(value = "Next question", key = {"n", "next"})
  @ShellMethodAvailability(value = "isStartedTesting")
  public String nextQuestion() {
    if (!isProcessedAllQuestions().isAvailable()) {
      Question question = questionsService.getQuestions().get(countProcessedQuestion);
      printQuestion(question);
      return creatSetAnswerMessage();
    } else {
      if (student.getMark() == 0) {
        student.setMark(testValidatorService.getMarkForQuestion(student.getAnswers()));
      }
      return creatTestingFinishMessage();
    }
  }

  @ShellMethod(value = "Set answer command", key = {"a", "answer"})
  @ShellMethodAvailability(value = "!isProcessedAllQuestions")
  public void setAnswer(String answer) {
    student.getAnswers().add(new Answer(questionsService.getQuestions().get(countProcessedQuestion).getTitleQuestion(), Integer.parseInt(answer)));
    countProcessedQuestion++;
    nextQuestion();
  }

  @ShellMethod(value = "Print result command", key = {"p", "print"})
  @ShellMethodAvailability(value = "isProcessedAllQuestions")
  public void printResult() {
    //   printResult(student);
  }

  private void printQuestion(Question question) {
    System.out.println(question.getTitleQuestion());
    int rowNumber = 1;
    for (String option : question.getAnswerOptions()) {
      System.out.println(rowNumber + ". " + option);
      rowNumber++;
    }
  }

  private Availability isProcessedAllQuestions() {
    return (countProcessedQuestion == questionsService.getQuestions().size()) ? Availability.available() : Availability.unavailable(creatNextQuestionMessage());
  }

  private Availability isStartedTesting() {
    return (countProcessedQuestion > 0) ? Availability.available() : Availability.unavailable(creatNextQuestionMessage());
  }

  private String createHelloMessage(String userName) {
    return messageSource.getMessage("shell.hello.message", new String[]{userName}, yamlProps.getLocale());
  }

  private String createWelcomeMessage() {
    return messageSource.getMessage("shell.welcome.message", new String[]{}, yamlProps.getLocale());
  }

  private String creatNextQuestionMessage() {
    return messageSource.getMessage("shell.next.question.message", new String[]{}, yamlProps.getLocale());
  }

  private String creatTestingFinishMessage() {
    return messageSource.getMessage("shell.testing.finish.message", new String[]{}, yamlProps.getLocale());
  }

  private String creatSetAnswerMessage() {
    return messageSource.getMessage("shell.set.answer.message", new String[]{}, yamlProps.getLocale());
  }

}

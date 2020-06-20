package ru.otus.hw04.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.*;
import ru.otus.hw04.impl.configs.YamlProps;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {

  private final QuestionsService questionsService;
  private final TestValidatorService testValidatorService;
  private final ReplyMessageService replyMessageService;
  private final OutputPrinterService outputPrinterService;
  private final YamlProps yamlProps;

  private List<Question> questions;
  private String studentName;
  private Student student;
  private int countProcessedQuestion = 0;

  @Override
  public String getWelcomeMessage() {
    return replyMessageService.getWelcomeMessage();
  }

  @Override
  public String getHelloMessage(String studentName) {
    this.studentName = studentName;
    return replyMessageService.getHelloMessage(studentName);
  }

  @Override
  public void startTesting() {
    this.student = new Student(studentName);
    this.questions = questionsService.getQuestions();
    nextQuestion();
  }

  @Override
  public void setAnswer(String answerString) {
    int answerOption = parseAndCheckAnswer(answerString);
    if (answerOption > 0) {
      Answer answer = new Answer(questions.get(countProcessedQuestion).getTitleQuestion(), answerOption);
      student.getAnswers().add(answer);
      countProcessedQuestion++;
      nextQuestion();
    } else {
      outputPrinterService.printlnMessage(replyMessageService.getErrorMessage());
    }
  }

  @Override
  public String printResult() {
    return replyMessageService.getResultMessage(student);
  }

  @Override
  public boolean isFinishTesting() {
    if (questions == null) return false;
    return countProcessedQuestion == questions.size();
  }

  @Override
  public boolean isStartedTesting() {
    return (student!=null);
  }

  @Override
  public boolean isCanStartTesting() {
    return (countProcessedQuestion == 0) && (studentName != null);
  }

  private int parseAndCheckAnswer(String answer) {
    int answerNum;
    int tryInput = 0;
    do {
      try {
        if (!checkOptions(Integer.parseInt(answer), questions.size())) {
          answerNum = 0;
          tryInput++;
        } else {
          answerNum = Integer.parseInt(answer);
        }
      } catch (Exception e) {
        answerNum = 0;
        tryInput++;
      }
    }
    while ((answerNum == 0) && (tryInput < yamlProps.getTryInputAnswer()));
    return answerNum;
  }

  private boolean checkOptions(int answer, int sizeAnswerOptions) {
    return answer >= 1 && answer <= sizeAnswerOptions;
  }

  private void nextQuestion() {
    if (!isFinishTesting()) {
      Question question = questions.get(countProcessedQuestion);
      printQuestion(question);
      outputPrinterService.printlnMessage(replyMessageService.getBeforeAnswerMessage());
    } else {
      if (student.getMark() == 0) {
        student.setMark(testValidatorService.getMarkForQuestion(student.getAnswers()));
      }
      outputPrinterService.printlnMessage(replyMessageService.getTestingFinishMessage());
    }
  }

  private void printQuestion(Question question) {
    outputPrinterService.printlnMessage(question.getTitleQuestion());
    int rowNumber = 1;
    for (String option : question.getAnswerOptions()) {
      outputPrinterService.printlnMessage(rowNumber + ". " + option);
      rowNumber++;
    }
  }

}

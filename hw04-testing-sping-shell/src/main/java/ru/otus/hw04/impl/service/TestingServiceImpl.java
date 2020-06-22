package ru.otus.hw04.impl.service;

import org.springframework.stereotype.Service;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.*;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

  private final QuestionsService questionsService;
  private final TestValidatorService testValidatorService;
  private final PrintService consolePrintService;

  private List<Question> questions;
  private Student student;
  private int countProcessedQuestion = 0;
  private Question actualQuestion;

  public TestingServiceImpl(QuestionsService questionsService, TestValidatorService testValidatorService, PrintService consolePrintService) {
    this.questionsService = questionsService;
    this.testValidatorService = testValidatorService;
    this.consolePrintService = consolePrintService;
    this.consolePrintService.printWelcomeMessage();
  }

  @Override
  public void createStudent(String studentName) {
    this.student = new Student(studentName);
    consolePrintService.printHelloMessage(student.getName());
  }

  @Override
  public void startTesting() {
    if (this.student != null) {
      this.questions = questionsService.getQuestions();
      nextQuestion();
    }
  }

  @Override
  public boolean setAnswer(String answerString) {
    if (this.student != null) {
      int answerOption = userInputValidation(answerString);
      if (answerOption > 0) {
        Answer answer = new Answer(actualQuestion.getTitleQuestion(), answerOption);
        student.getAnswers().add(answer);
        countProcessedQuestion++;
        nextQuestion();
        return true;
      } else {
        consolePrintService.printErrorMessage();
      }
    }
    return false;
  }

  @Override
  public void printResult() {
    consolePrintService.printResult(student);
  }

  @Override
  public boolean isFinishTesting() {
    if (questions == null) return false;
    return countProcessedQuestion == questions.size();
  }

  @Override
  public boolean isStartedTesting() {
    return (student != null);
  }

  @Override
  public boolean isCanStartTesting() {
    return (countProcessedQuestion == 0) && (student != null);
  }

  private void nextQuestion() {
    if (!isFinishTesting()) {
      actualQuestion = questions.get(countProcessedQuestion);
      consolePrintService.printQuestion(actualQuestion);
      consolePrintService.printBeforeAnswerMessage();
    } else {
      if (student.getMark() == 0) {
        student.setMark(testValidatorService.getMarkForQuestion(student.getAnswers()));
      }
      consolePrintService.printTestingFinishMessage();
    }
  }

  private int userInputValidation(String answer) {
    int answerNum;
    try {
      if (!checkOptions(Integer.parseInt(answer), questions.size())) {
        answerNum = 0;
      } else {
        answerNum = Integer.parseInt(answer);
      }
    } catch (Exception e) {
      answerNum = 0;
    }
    return answerNum;
  }

  private boolean checkOptions(int answer, int sizeAnswerOptions) {
    return answer >= 1 && answer <= sizeAnswerOptions;
  }


}

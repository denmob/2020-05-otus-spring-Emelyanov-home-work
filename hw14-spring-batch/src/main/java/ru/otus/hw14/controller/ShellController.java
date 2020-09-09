package ru.otus.hw14.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@ShellComponent
@RequiredArgsConstructor
public class ShellController {

  private static final String SUCCESS_OPERATION = "Success operation";
  private static final String FAILURE_OPERATION = "Failure operation";
  private static final String ILLEGAL_ARGUMENTS = "Illegal Arguments";

  @ShellMethod(value = "", key = {"s1", "start ops 1"})
  public String mongoToMemory() {
    return SUCCESS_OPERATION;
  }

  @ShellMethod(value = "", key = {"s2", "start ops 2"})
  public String memoryToPostgre() {
    return SUCCESS_OPERATION;
  }
}

package ru.otus.hw05.impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw05.core.controller.LibraryController;

@ShellComponent
@RequiredArgsConstructor
public class ShellLibraryController implements LibraryController {


  @ShellMethod(value = " command", key = {"??"})
  @ShellMethodAvailability(value = "isAvailable")
  void view() {
    // to do
  }

  Availability isAvailable() {
    return true ? Availability.available() : Availability.unavailable("");
  }

}

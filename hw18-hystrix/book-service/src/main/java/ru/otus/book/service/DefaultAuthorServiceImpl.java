package ru.otus.book.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.library.model.Author;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DefaultAuthorServiceImpl implements DefaultAuthorService {

  private final Map<String, Author> authors = new HashMap<>();

  public DefaultAuthorServiceImpl() {
    createData();
  }

  @Override
  public Author getAuthorByLastName(String lastName) {
    return authors.get(lastName);
  }

  private void createData() {
    authors.put("Langr", Author.builder().firstName("Jeff").lastName("Langr").birthday(convertStringToDate("1969-11-08")).build());
    authors.put("Bloch", Author.builder().firstName("Joshua").lastName("Bloch").birthday(convertStringToDate("1961-08-29")).build());
    authors.put("Horstmann", Author.builder().firstName("Cay S.").lastName("Horstmann").birthday(convertStringToDate("1959-03-19")).build());
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return new SimpleDateFormat("yyyy-MM-dd").parse(date);
  }
}

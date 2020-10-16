package ru.otus.book.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.library.model.Author;
import ru.otus.library.model.Genre;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DefaultDataServiceImpl implements DefaultDataService {

  private final Map<String, Author> authors = new HashMap<>();
  private final Map<String, Genre> genres = new HashMap<>();

  public DefaultDataServiceImpl() {
    createData();
  }

  @Override
  public Author getAuthorByLastName(String lastName) {
    return authors.get(lastName);
  }

  @Override
  public Genre getGenreByName(String name) {
    return genres.get(name);
  }

  private void createData() {
    authors.put("Langr", Author.builder().firstName("Jeff").lastName("Langr").birthday(convertStringToDate("1969-11-08")).build());
    authors.put("Bloch", Author.builder().firstName("Joshua").lastName("Bloch").birthday(convertStringToDate("1961-08-29")).build());
    authors.put("Horstmann", Author.builder().firstName("Cay S.").lastName("Horstmann").birthday(convertStringToDate("1959-03-19")).build());
    genres.put("Programming", Genre.builder().name("Programming").build());
    genres.put("Science", Genre.builder().name("Science").build());
    genres.put("Software", Genre.builder().name("Software").build());
  }

  @SneakyThrows
  private Date convertStringToDate(String date) {
    return new SimpleDateFormat("yyyy-MM-dd").parse(date);
  }
}

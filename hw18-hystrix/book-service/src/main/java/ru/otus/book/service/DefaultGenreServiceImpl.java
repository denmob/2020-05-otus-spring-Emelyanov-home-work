package ru.otus.book.service;

import org.springframework.stereotype.Service;
import ru.otus.library.model.Genre;
import ru.otus.library.model.dto.GenreDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultGenreServiceImpl implements DefaultGenreService {

  private final Map<String, Genre> genres = new HashMap<>();

  public DefaultGenreServiceImpl() {
    createData();
  }

  @Override
  public GenreDto getGenreByName(String name) {
    return GenreDto.toDto(genres.get(name));
  }

  private void createData() {
    genres.put("Programming", Genre.builder().name("Programming").build());
    genres.put("Science", Genre.builder().name("Science").build());
    genres.put("Software", Genre.builder().name("Software").build());
  }
}

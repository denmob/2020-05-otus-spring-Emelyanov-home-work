package ru.otus.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.model.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {

  private String id;

  private String name;

  public static GenreDto toDto(Genre genre) {
    return new GenreDto(genre.getId(), genre.getName());
  }

  public static Genre toGenre(GenreDto genreDto) {
    return new Genre(genreDto.getId(), genreDto.getName());
  }
}

package ru.otus.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.model.Author;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

  private String id;

  private String firstName;

  private String lastName;

  private Date birthday;

  public static AuthorDto toDto(Author author) {
    return new AuthorDto(author.getId(), author.getFirstName(), author.getLastName(), author.getBirthday());
  }

  public static Author toAuthor(AuthorDto authorDto) {
    return new Author(authorDto.getId(), authorDto.getFirstName(), authorDto.getLastName(), authorDto.getBirthday());
  }
}

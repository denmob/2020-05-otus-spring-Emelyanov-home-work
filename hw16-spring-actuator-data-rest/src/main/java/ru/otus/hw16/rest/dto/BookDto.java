package ru.otus.hw16.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw16.model.Author;
import ru.otus.hw16.model.Book;
import ru.otus.hw16.model.Genre;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

  private String id;

  private String title;

  private Date date;

  private Author author;

  private Genre genre;

  public static BookDto toDto(Book book) {
    return new BookDto(book.getId(), book.getTitle(), book.getDate(), book.getAuthor(), book.getGenre());
  }
}

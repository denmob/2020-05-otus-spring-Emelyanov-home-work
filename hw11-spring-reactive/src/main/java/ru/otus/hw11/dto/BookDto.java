package ru.otus.hw11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Genre;

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

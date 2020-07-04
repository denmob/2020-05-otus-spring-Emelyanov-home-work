package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;
import ru.otus.hw06.core.service.OutputPrintService;
import ru.otus.hw06.core.service.ViewRepositoryService;
import ru.otus.hw06.impl.repositories.AuthorRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.BookRepositoryJpaImpl;
import ru.otus.hw06.impl.repositories.GenreRepositoryJpaImpl;

@Service
@RequiredArgsConstructor
public class ViewRepositoryServiceImpl implements ViewRepositoryService {


  private final AuthorRepositoryJpaImpl authorRepositoryJpa;
  private final BookRepositoryJpaImpl bookRepositoryJpa;
  private final GenreRepositoryJpaImpl genreRepositoryJpa;
  private final OutputPrintService consolePrintService;
  private final CommentRepositoryJpa commentRepositoryJpa;

  @Override
  public void printTableBooks() {
    for (Book book:bookRepositoryJpa.getAll()) {
      consolePrintService.printlnMessage(book.toString());
    }
  }

  @Override
  public void printTableAuthors() {
    for (Author author:authorRepositoryJpa.getAll()) {
      consolePrintService.printlnMessage(author.toString());
    }
  }

  @Override
  public void printTableGenres() {
    for (Genre genre:genreRepositoryJpa.getAll()) {
      consolePrintService.printlnMessage(genre.toString());
    }
  }

  @Override
  public void printTableComments() {
    for (Comment comment:commentRepositoryJpa.getAll()) {
      consolePrintService.printlnMessage(comment.toString());
    }
  }
}

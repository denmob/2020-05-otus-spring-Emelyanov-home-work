package ru.otus.hw07.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw07.core.models.Author;
import ru.otus.hw07.core.models.Book;
import ru.otus.hw07.core.models.Comment;
import ru.otus.hw07.core.models.Genre;
import ru.otus.hw07.core.repositories.AuthorRepository;
import ru.otus.hw07.core.repositories.BookRepository;
import ru.otus.hw07.core.repositories.CommentRepository;
import ru.otus.hw07.core.repositories.GenreRepository;
import ru.otus.hw07.core.service.OutputPrintService;
import ru.otus.hw07.core.service.ViewRepositoryService;


@Service
@RequiredArgsConstructor
public class ViewRepositoryServiceImpl implements ViewRepositoryService {

  private final AuthorRepository authorRepositoryJpa;
  private final BookRepository bookRepositoryJpa;
  private final GenreRepository genreRepositoryJpa;
  private final CommentRepository commentRepository;
  private final OutputPrintService consolePrintService;

  @Override
  public void printTableBooks() {
    for (Book book : bookRepositoryJpa.findAll()) {
      consolePrintService.printlnMessage(book.toString());
    }
  }

  @Override
  public void printTableAuthors() {
    for (Author author : authorRepositoryJpa.findAll()) {
      consolePrintService.printlnMessage(author.toString());
    }
  }

  @Override
  public void printTableGenres() {
    for (Genre genre : genreRepositoryJpa.findAll()) {
      consolePrintService.printlnMessage(genre.toString());
    }
  }

  @Override
  public void printTableComments() {
    for (Comment comment : commentRepository.findAll()) {
      consolePrintService.printlnMessage(comment.toString());
    }
  }
}

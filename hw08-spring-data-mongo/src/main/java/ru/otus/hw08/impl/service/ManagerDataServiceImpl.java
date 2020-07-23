package ru.otus.hw08.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.models.Genre;
import ru.otus.hw08.core.service.ManagerDataService;
import ru.otus.hw08.core.service.ViewRepositoryService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerDataServiceImpl implements ManagerDataService {

  private final CRUDBookService crudBookService;
  private final CRUDCommentService crudCommentService;
  private final CRUDAuthorService crudAuthorService;
  private final CRUDGenreService crudGenreService;
  private final ViewRepositoryService viewRepositoryService;

  @Override
  public boolean createBook(Book book) {
    return crudBookService.create(book) != null;
  }

  @Override
  public Optional<BookWithComments> readBookByTitle(String bookTitle) {
    return crudBookService.readWithComments(bookTitle);
  }

  @Override
  public boolean deleteBookByTitle(String bookTitle) {
    return crudBookService.deleteByTitleEquals(bookTitle);
  }

  @Override
  public boolean createComment(String bookTitle, String commentary) {
    Optional<Book> optionalBook = crudBookService.readByTitleEquals(bookTitle);
    if (optionalBook.isPresent()) {
      Comment comment = Comment.builder().bookId(optionalBook.get().getId()).commentary(commentary).timestamp(new Date()).build();
      crudCommentService.create(comment);
    }
    return false;
  }

  @Override
  public List<Comment> readComments(String partComment) {
    return crudCommentService.readCommentaryContains(partComment);
  }

  @Override
  public boolean deleteComment(String partComment) {
    return crudCommentService.deleteCommentaryContains(partComment);
  }

  @Override
  public void printTableBooks() {
    viewRepositoryService.printTableBooks();
  }

  @Override
  public void printTableAuthors() {
    viewRepositoryService.printTableAuthors();
  }

  @Override
  public void printTableGenres() {
    viewRepositoryService.printTableGenres();
  }

  @Override
  public void printTableComments() {
    viewRepositoryService.printTableComments();
  }

  @Override
  public Optional<Author> readAuthorByLastName(String lastName) {
    return crudAuthorService.readLastNameEquals(lastName);
  }

  @Override
  public Optional<Genre> readGenreByName(String name) {
    return Optional.empty();
  }
}

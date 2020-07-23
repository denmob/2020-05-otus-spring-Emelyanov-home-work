package ru.otus.hw08.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Author;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;
import ru.otus.hw08.core.models.Genre;
import ru.otus.hw08.core.service.ViewRepositoryService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CRUDBookService.class, CRUDCommentService.class,
    CRUDAuthorService.class, CRUDGenreService.class, ViewRepositoryService.class, ManagerDataServiceImpl.class})
class ManagerDataServiceImplTest {

  @MockBean
  private CRUDBookService crudBookService;
  @MockBean
  private CRUDCommentService crudCommentService;
  @MockBean
  private CRUDAuthorService crudAuthorService;
  @MockBean
  private CRUDGenreService crudGenreService;
  @MockBean
  private ViewRepositoryService viewRepositoryService;

  @Autowired
  private ManagerDataServiceImpl managerDataService;

  @Test
  void createBook() {
    Book book = new Book();
    when(crudBookService.create(book)).thenReturn(book);

    managerDataService.createBook(book);

    verify(crudBookService, times(1)).create(book);
  }

  @Test
  void readBookByTitle() {
    Book book = new Book();
    BookWithComments bookWithComments = new BookWithComments();
    when(crudBookService.readWithComments(book.getTitle())).thenReturn(Optional.of(bookWithComments));

    managerDataService.readBookByTitle(book.getTitle());

    verify(crudBookService, times(1)).readWithComments(book.getTitle());
  }

  @Test
  void deleteBookByTitle() {
    Book book = new Book();
    when(crudBookService.deleteByTitleEquals(book.getTitle())).thenReturn(true);

    managerDataService.deleteBookByTitle(book.getTitle());

    verify(crudBookService, times(1)).deleteByTitleEquals(book.getTitle());
  }

  @Test
  void createComment() {
    Book book = new Book();
    Comment comment = new Comment();
    comment.setCommentary("new");
    when(crudBookService.readByTitleContains(book.getTitle())).thenReturn(Optional.of(book));
    when(crudCommentService.create(any())).thenReturn(comment);

    managerDataService.createComment(book.getTitle(), comment.getCommentary());

    verify(crudBookService, times(1)).readByTitleContains(book.getTitle());
    verify(crudCommentService, times(1)).create(any());
  }

  @Test
  void readComments() {
    Comment comment = new Comment();
    comment.setCommentary("new");
    when(crudCommentService.readCommentaryContains(comment.getCommentary())).thenReturn(anyList());

    managerDataService.readComments(comment.getCommentary());

    verify(crudCommentService, times(1)).readCommentaryContains(comment.getCommentary());
  }

  @Test
  void deleteComment() {
    Comment comment = new Comment();
    comment.setCommentary("new");
    when(crudCommentService.deleteCommentaryContains(comment.getCommentary())).thenReturn(true);

    managerDataService.deleteComment(comment.getCommentary());

    verify(crudCommentService, times(1)).deleteCommentaryContains(comment.getCommentary());
  }

  @Test
  void printTableBooks() {
    doNothing().when(viewRepositoryService).printTableBooks();

    managerDataService.printTableBooks();

    verify(viewRepositoryService, times(1)).printTableBooks();
  }

  @Test
  void printTableAuthors() {
    doNothing().when(viewRepositoryService).printTableAuthors();

    managerDataService.printTableAuthors();

    verify(viewRepositoryService, times(1)).printTableAuthors();
  }

  @Test
  void printTableGenres() {
    doNothing().when(viewRepositoryService).printTableGenres();

    managerDataService.printTableGenres();

    verify(viewRepositoryService, times(1)).printTableGenres();
  }

  @Test
  void printTableComments() {
    doNothing().when(viewRepositoryService).printTableComments();

    managerDataService.printTableComments();

    verify(viewRepositoryService, times(1)).printTableComments();
  }

  @Test
  void readAuthorByLastName() {
    Author author = new Author();
    when(crudAuthorService.readLastNameEquals(author.getLastName())).thenReturn(Optional.of(author));

    assertEquals(author, managerDataService.readAuthorByLastName(author.getLastName()).get());

    verify(crudAuthorService, times(1)).readLastNameEquals(author.getLastName());
  }

  @Test
  void readGenreByName() {
    Genre genre = new Genre();
    when(crudGenreService.readNameEquals(genre.getName())).thenReturn(Optional.of(genre));

    assertEquals(genre, managerDataService.readGenreByName(genre.getName()).get());

    verify(crudGenreService, times(1)).readNameEquals(genre.getName());
  }
}

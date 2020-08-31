package ru.otus.hw12.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.otus.hw12.model.Book;
import ru.otus.hw12.service.AuthorServiceImpl;
import ru.otus.hw12.service.BookServiceImpl;
import ru.otus.hw12.service.CommentServiceImpl;
import ru.otus.hw12.service.GenreServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest(classes = {BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class, BookController.class, CommentServiceImpl.class})
class BookControllerTest {

  @MockBean
  private BookServiceImpl bookService;
  @MockBean
  private AuthorServiceImpl authorService;
  @MockBean
  private GenreServiceImpl genreService;
  @MockBean
  private CommentServiceImpl commentService;

  @Autowired
  private BookController bookController;

  @Test
  void listBookPage() {
    int countBook = 3;
    Model model = new ConcurrentModel();
    List<Book> list = new ArrayList<>();
    list.add(new Book());
    list.add(new Book());
    Page<Book> books = new PageImpl<>(list);

    when(bookService.getLastAddedBooks(countBook)).thenReturn(books);

    assertNull(model.getAttribute("books"));
    assertEquals("book/list", bookController.listBookPage(countBook, model));
    assertNotNull(model.getAttribute("books"));
    assertEquals(books, model.getAttribute("books"));

    verify(bookService, times(1)).getLastAddedBooks(countBook);
  }

  @Test
  void createBookPage() {
    Model model = new ConcurrentModel();

    when(genreService.findAll()).thenReturn(new ArrayList<>());
    when(authorService.findAll()).thenReturn(new ArrayList<>());

    assertEquals("book/create", bookController.createBookPage(model));

    assertNotNull(model.getAttribute("book"));
    assertNotNull(model.getAttribute("authors"));
    assertNotNull(model.getAttribute("genres"));

    verify(authorService, times(1)).findAll();
    verify(genreService, times(1)).findAll();
  }

  @Test
  void saveBook() {
    Book book = new Book();

    when(bookService.save(book)).thenReturn(book);

    assertEquals("redirect:/book/list", bookController.saveBook(book));

    verify(bookService, times(1)).save(book);
  }

  @Test
  void editBookPage() {
    Book book = new Book();
    Model model = new ConcurrentModel();

    when(genreService.findAll()).thenReturn(new ArrayList<>());
    when(authorService.findAll()).thenReturn(new ArrayList<>());
    when(bookService.readBookById(book.getId())).thenReturn(Optional.of(book));

    assertEquals("book/edit", bookController.editBookPage(book.getId(), model));

    verify(bookService, times(1)).readBookById(book.getId());
  }

  @Test
  void editBookPageException() {
    Book book = new Book();
    Model model = new ConcurrentModel();

    when(bookService.readBookById(book.getId())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> bookController.editBookPage(book.getId(), model));
  }

  @Test
  void deleteBook() {
    Book book = new Book();

    when(bookService.deleteBookById(book.getId())).thenReturn(true);
    when(commentService.deleteCommentAllByBookId(book.getId())).thenReturn(true);

    assertEquals("redirect:/book/list", bookController.deleteBook(book.getId()));

    verify(bookService, times(1)).deleteBookById(book.getId());
    verify(commentService, times(1)).deleteCommentAllByBookId(book.getId());
  }
}

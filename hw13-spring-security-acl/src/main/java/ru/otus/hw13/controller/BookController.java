package ru.otus.hw13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw13.model.Book;
import ru.otus.hw13.service.AuthorService;
import ru.otus.hw13.service.BookService;
import ru.otus.hw13.service.CommentService;
import ru.otus.hw13.service.GenreService;

@Controller
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;
  private final CommentService commentService;

  @GetMapping("/book/list")
  public String listBookPage(@RequestParam(value = "countBook", defaultValue = "5") int countBook, Model model) {
    Page<Book> books = bookService.getLastAddedBooks(countBook);
    model.addAttribute("books", books);
    return "book/list";
  }

  @GetMapping("/book/create")
  public String createBookPage(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "book/create";
  }

  @GetMapping("/book/edit")
  public String editBookPage(@RequestParam("id") String id, Model model) {
    Book book = bookService.readBookById(id).orElseThrow(() -> new NotFoundException("Not found entry book.id: " + id));
    model.addAttribute("book", book);
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "book/edit";
  }

  @PreAuthorize("hasPermission(#book, 'CUSTOM') or hasRole('ADMIN')")
  @PostMapping("/book/save")
  public String saveBook(@ModelAttribute Book book) {
    bookService.save(book);
    return "redirect:/book/list";
  }

  @PostMapping("/book/delete")
  public String deleteBook(@RequestParam("id") String id) {
    if (bookService.deleteBookById(id)) {
      commentService.deleteCommentAllByBookId(id);
    }
    return "redirect:/book/list";
  }
}

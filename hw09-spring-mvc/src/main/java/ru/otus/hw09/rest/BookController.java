package ru.otus.hw09.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.service.AuthorService;
import ru.otus.hw09.service.BookService;
import ru.otus.hw09.service.GenreService;

@Controller
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  @GetMapping("/")
  public String listBookPage(@RequestParam(value = "countBook", defaultValue = "5") int countBook, Model model) {
    Page<Book> books = bookService.getLastAddedBooks(countBook);
    model.addAttribute("books", books);
    return "listBook";
  }

  @GetMapping("/createBook")
  public String createBookPage(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "createBook";
  }

  @PostMapping("/saveBook")
  public String saveBook(@ModelAttribute Book book) {
    bookService.save(book);
    return "redirect:/";
  }

  @GetMapping("/editBook")
  public String editBookPage(@RequestParam("id") String id, Model model) {
    Book book = bookService.readBookById(id).orElseThrow(NotFoundException::new);
    model.addAttribute("book", book);
    model.addAttribute("authors", authorService.findAll());
    model.addAttribute("genres", genreService.findAll());
    return "editBook";
  }

  @PostMapping("/deleteBook")
  public String deleteBook(@RequestParam("id") String id) {
    bookService.deleteBookById(id);
    return "redirect:/";
  }
}

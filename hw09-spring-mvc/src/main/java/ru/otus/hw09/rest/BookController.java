package ru.otus.hw09.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.hw09.dto.BookWithComments;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.service.BookService;

@Controller
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("/")
  public String listPage(Model model) {
    Page<Book> books = bookService.getLastAddedBooks(5);
    model.addAttribute("books", books);
    return "listBook";
  }

  @GetMapping("/edit")
  public String editPage(@RequestParam("id") String id, Model model) {
    Book book = bookService.readBookById(id).orElseThrow(NotFoundException::new);
    model.addAttribute("book", book);
    return "editBook";
  }

  @GetMapping("/comments")
  public String viewPage(@RequestParam("id") String id, Model model) {
    BookWithComments bookWithComments = bookService.readBookWithCommentsById(id).orElseThrow(NotFoundException::new);
    model.addAttribute("bookWithComments", bookWithComments);
    return "listComment";
  }

  @PostMapping("/edit")
  public String savePerson(Book book) {
    bookService.save(book);
    return "redirect:/";
  }

  @ExceptionHandler(NullPointerException.class)
  public ModelAndView handleNPE(NullPointerException e) {
    ModelAndView modelAndView = new ModelAndView("error");
    modelAndView.addObject("message", e.getMessage());
    return modelAndView;
  }
}

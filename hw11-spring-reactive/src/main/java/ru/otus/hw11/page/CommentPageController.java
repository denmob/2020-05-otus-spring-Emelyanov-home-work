package ru.otus.hw11.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CommentPageController {

  @GetMapping("/pageCommentList/{bookId},{title}")
  public String commentsPage(@PathVariable("bookId") String bookId, @PathVariable("title") String title, Model model) {
    model.addAttribute("bookId", bookId);
    model.addAttribute("title", title);
    return "pageCommentList";
  }
}

package ru.otus.hw09.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw09.model.Comment;
import ru.otus.hw09.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping("/viewComment")
  public String viewCommentPage(@RequestParam("id") String id, @RequestParam("title") String title, Model model) {
    List<Comment> comments = commentService.readAllForBook(id);
    model.addAttribute("comments", comments);
    model.addAttribute("title", title);
    return "listComment";
  }
}

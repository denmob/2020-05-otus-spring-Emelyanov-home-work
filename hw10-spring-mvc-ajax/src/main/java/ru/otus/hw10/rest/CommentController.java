package ru.otus.hw10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw10.service.CommentService;


@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

}

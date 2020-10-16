package ru.otus.comment.service;

import ru.otus.library.model.Book;

public interface BookService {

  Book getBookByTitle(String title);
}

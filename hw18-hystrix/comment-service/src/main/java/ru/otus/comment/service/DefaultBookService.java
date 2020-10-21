package ru.otus.comment.service;

import ru.otus.library.model.Book;

public interface DefaultBookService {

  Book getBookByTitle(String title);
}

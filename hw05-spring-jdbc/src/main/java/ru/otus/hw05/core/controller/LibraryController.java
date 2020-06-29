package ru.otus.hw05.core.controller;

import java.util.Date;

public interface LibraryController {

    void createBook(String title, Date date, Long authorId, Long genreId);
}

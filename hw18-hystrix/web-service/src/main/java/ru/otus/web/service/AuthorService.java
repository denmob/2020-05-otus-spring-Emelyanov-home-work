package ru.otus.web.service;

import ru.otus.library.model.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

  List<AuthorDto> getAuthors();

  AuthorDto getAuthorById(String id);
}

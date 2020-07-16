package ru.otus.hw07.core.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw07.core.models.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}

package ru.otus.hw07.core.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw07.core.models.Genre;

public interface GenreRepository extends CrudRepository<Genre,Long> {
}

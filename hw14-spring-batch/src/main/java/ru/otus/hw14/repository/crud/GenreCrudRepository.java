package ru.otus.hw14.repository.crud;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.table.Genre;

import java.util.Optional;

public interface GenreCrudRepository extends CrudRepository<Genre,Long> {

  Optional<Genre> findByName(String name);
}

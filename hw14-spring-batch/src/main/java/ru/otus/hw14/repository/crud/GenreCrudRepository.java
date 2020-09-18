package ru.otus.hw14.repository.crud;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.entity.GenreEntity;

import java.util.Optional;

public interface GenreCrudRepository extends CrudRepository<GenreEntity,Long> {

  Optional<GenreEntity> findByName(String name);
}

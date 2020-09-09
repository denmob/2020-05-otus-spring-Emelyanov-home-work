package ru.otus.hw14.repository.crud;


import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.table.Genre;

public interface GenreCrudRepository extends CrudRepository<Genre,Long> {
}

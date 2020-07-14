package ru.otus.hw07.core.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw07.core.models.Book;

public interface BookRepository extends CrudRepository<Book,Long> {
}

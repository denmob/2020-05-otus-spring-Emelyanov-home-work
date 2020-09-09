package ru.otus.hw14.repository.crud;


import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.table.Book;

public interface BookCrudRepository extends CrudRepository<Book,Long> {
}

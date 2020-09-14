package ru.otus.hw14.repository.crud;


import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.table.Book;

import java.util.Date;
import java.util.Optional;

public interface BookCrudRepository extends CrudRepository<Book,Long> {

  Optional<Book> findByTitleAndDate(String title, Date date);
}

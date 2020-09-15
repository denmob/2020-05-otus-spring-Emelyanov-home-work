package ru.otus.hw14.repository.crud;


import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.entity.BookEntity;

import java.util.Date;
import java.util.Optional;

public interface BookCrudRepository extends CrudRepository<BookEntity,Long> {

  Optional<BookEntity> findByTitleAndDate(String title, Date date);
}

package ru.otus.hw14.repository.crud;


import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.table.Author;

import java.util.Date;
import java.util.Optional;

public interface AuthorCrudRepository extends CrudRepository<Author, Long> {

  Optional<Author> findByFirstNameAndLastNameAndBirthday(String firstName, String lastName, Date birthday);
}

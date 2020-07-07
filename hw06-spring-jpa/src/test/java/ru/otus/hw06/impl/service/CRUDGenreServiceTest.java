package ru.otus.hw06.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.repositories.GenreRepositoryJpa;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDGenreService.class)
class CRUDGenreServiceTest {

  @MockBean
  private GenreRepositoryJpa genreRepositoryJpa;

  @Autowired
  private CRUDGenreService crudGenreService;

  @Test
  void create() {
    Genre genre = new Genre(0L, "newGenre");
    when(genreRepositoryJpa.insert(genre)).thenReturn(genre);

    crudGenreService.create(genre);
    verify(genreRepositoryJpa, times(1)).insert(genre);
  }

  @Test
  void read() {
    long id = 1L;
    when(genreRepositoryJpa.getById(id)).thenReturn(any());

    crudGenreService.read(id);
    verify(genreRepositoryJpa, times(1)).getById(id);
  }

  @Test
  void delete() {
    long id = 1L;
    when(genreRepositoryJpa.deleteById(id)).thenReturn(true);

    crudGenreService.delete(id);
    verify(genreRepositoryJpa, times(1)).deleteById(id);
  }

  @Test
  void update() {
    Genre genre = new Genre(1L, "newGenre");
    when(genreRepositoryJpa.insert(genre)).thenReturn(genre);

    crudGenreService.update(genre);
    verify(genreRepositoryJpa, times(1)).insert(genre);
  }
}

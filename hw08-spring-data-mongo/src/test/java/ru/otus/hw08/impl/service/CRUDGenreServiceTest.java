package ru.otus.hw08.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw08.core.models.Genre;
import ru.otus.hw08.core.repositories.GenreRepository;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDGenreService.class)
class CRUDGenreServiceTest {

  @MockBean
  private GenreRepository genreRepository;

  @Autowired
  private CRUDGenreService crudGenreService;

  private Genre newGenre;
  private Genre oldGenre;

  @BeforeEach
  void beforeEach() {
    newGenre = new Genre("0", "newGenre");
    oldGenre = new Genre("1", "oldGenre");
  }

  @Test
  void create() {
    when(genreRepository.save(newGenre)).thenReturn(newGenre);

    crudGenreService.create(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }

  @Test
  void read() {
    when(genreRepository.findByNameEquals(oldGenre.getName())).thenReturn(any());

    crudGenreService.readNameEquals(oldGenre.getName());
    verify(genreRepository, times(1)).findByNameEquals(oldGenre.getName());
  }

  @Test
  void delete() {
    when(genreRepository.deleteByNameEquals(oldGenre.getName())).thenReturn(1L);

    crudGenreService.deleteNameEquals(oldGenre.getName());
    verify(genreRepository, times(1)).deleteByNameEquals(oldGenre.getName());
  }

  @Test
  void update() {
    when(genreRepository.save(newGenre)).thenReturn(newGenre);

    crudGenreService.update(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }
}

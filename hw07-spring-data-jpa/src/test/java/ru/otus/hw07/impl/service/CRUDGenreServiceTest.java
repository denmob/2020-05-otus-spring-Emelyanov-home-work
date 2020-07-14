package ru.otus.hw07.impl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw07.core.models.Genre;
import ru.otus.hw07.core.repositories.GenreRepository;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CRUDGenreService.class)
class CRUDGenreServiceTest {

  @MockBean
  private GenreRepository genreRepository;

  @Autowired
  private CRUDGenreService crudGenreService;

  @Test
  void create() {
    Genre genre = new Genre(0L, "newGenre");
    when(genreRepository.save(genre)).thenReturn(genre);

    crudGenreService.create(genre);
    verify(genreRepository, times(1)).save(genre);
  }

  @Test
  void read() {
    long id = 1L;
    when(genreRepository.findById(id)).thenReturn(any());

    crudGenreService.read(id);
    verify(genreRepository, times(1)).findById(id);
  }

  @Test
  void delete() {
    long id = 1L;
    doNothing().when(genreRepository).deleteById(id);

    crudGenreService.delete(id);
    verify(genreRepository, times(1)).deleteById(id);
  }

  @Test
  void update() {
    Genre genre = new Genre(1L, "newGenre");
    when(genreRepository.save(genre)).thenReturn(genre);

    crudGenreService.update(genre);
    verify(genreRepository, times(1)).save(genre);
  }
}

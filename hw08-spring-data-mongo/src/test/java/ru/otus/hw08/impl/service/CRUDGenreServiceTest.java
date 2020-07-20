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

  @BeforeEach
  void beforeEach(){
    newGenre = new Genre("0", "newGenre");
  }

  @Test
  void create() {
    when(genreRepository.save(newGenre)).thenReturn(newGenre);

    crudGenreService.create(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }

  @Test
  void read() {
    String id = "1";
    when(genreRepository.findById(id)).thenReturn(any());

    crudGenreService.read(id);
    verify(genreRepository, times(1)).findById(id);
  }

  @Test
  void delete() {
    String id = "1";
    doNothing().when(genreRepository).deleteById(id);

    crudGenreService.delete(id);
    verify(genreRepository, times(1)).deleteById(id);
  }

  @Test
  void update() {
    when(genreRepository.save(newGenre)).thenReturn(newGenre);

    crudGenreService.update(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }
}

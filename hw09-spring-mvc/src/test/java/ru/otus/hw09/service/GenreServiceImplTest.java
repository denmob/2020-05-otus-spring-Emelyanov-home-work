package ru.otus.hw09.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw09.model.Genre;
import ru.otus.hw09.repository.GenreRepository;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = GenreServiceImpl.class)
class GenreServiceImplTest {

  @MockBean
  private GenreRepository genreRepository;

  @Autowired
  private GenreServiceImpl genreService;

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

    genreService.create(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }

  @Test
  void read() {
    when(genreRepository.findByNameEquals(oldGenre.getName())).thenReturn(any());

    genreService.readNameEquals(oldGenre.getName());
    verify(genreRepository, times(1)).findByNameEquals(oldGenre.getName());
  }

  @Test
  void delete() {
    when(genreRepository.deleteByNameEquals(oldGenre.getName())).thenReturn(1L);

    genreService.deleteNameEquals(oldGenre.getName());
    verify(genreRepository, times(1)).deleteByNameEquals(oldGenre.getName());
  }

  @Test
  void update() {
    when(genreRepository.save(newGenre)).thenReturn(newGenre);

    genreService.update(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }
}

package ru.otus.hw16.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw16.model.Genre;
import ru.otus.hw16.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
  void save() {
    when(genreRepository.save(newGenre)).thenReturn(newGenre);

    genreService.save(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }

  @Test
  void findByNameEquals() {
    when(genreRepository.findByNameEquals(oldGenre.getName())).thenReturn(any());

    genreService.findByNameEquals(oldGenre.getName());
    verify(genreRepository, times(1)).findByNameEquals(oldGenre.getName());
  }

  @Test
  void deleteByNameEquals() {
    when(genreRepository.deleteByNameEquals(oldGenre.getName())).thenReturn(1L);

    genreService.deleteByNameEquals(oldGenre.getName());
    verify(genreRepository, times(1)).deleteByNameEquals(oldGenre.getName());
  }

  @Test
  void update() {
    when(genreRepository.save(newGenre)).thenReturn(newGenre);

    genreService.save(newGenre);
    verify(genreRepository, times(1)).save(newGenre);
  }

  @Test
  void findAll() {
    List<Genre> genres = new ArrayList<>();
    genres.add(new Genre());
    genres.add(new Genre());
    when(genreRepository.findAll()).thenReturn(genres);

    List<Genre> actual = genreService.findAll();
    assertEquals(genres, actual);

    verify(genreRepository, times(1)).findAll();
  }

  @Test
  void findById() {
    when(genreRepository.findById(oldGenre.getName())).thenReturn(any());

    genreService.findById(oldGenre.getName());
    verify(genreRepository, times(1)).findById(oldGenre.getName());
  }
}

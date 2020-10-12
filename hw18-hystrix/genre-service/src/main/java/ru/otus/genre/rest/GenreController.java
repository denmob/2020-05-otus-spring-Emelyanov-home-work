package ru.otus.genre.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.otus.genre.service.GenreService;
import ru.otus.library.handler.NotFoundException;
import ru.otus.library.model.Genre;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {

  private final GenreService genreService;

  @GetMapping("/api/genre")
  public List<Genre> getGenres() {
    return genreService.findAll();
  }

  @GetMapping("/api/genre/name")
  public Genre getGenreName(@RequestParam(value = "genreName") String genreName) {
    return genreService.findByNameEquals(genreName).orElseThrow(() -> new NotFoundException("Not found entry genre.name: " + genreName));
  }

  @GetMapping("/api/genre/id")
  public Genre getGenreId(@RequestParam(value = "genreId") String genreId) {
    return genreService.findById(genreId).orElseThrow(() -> new NotFoundException("Not found entry genre.id: " + genreId));
  }
}

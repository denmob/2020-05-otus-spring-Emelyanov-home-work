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

  @GetMapping(value = "/api/genre", params = "name")
  public Genre getGenreName(@RequestParam(value = "name") String name) {
    return genreService.findByNameEquals(name).orElseThrow(() -> new NotFoundException("Not found entry genre.name: " + name));
  }

  @GetMapping(value = "/api/genre", params = "id")
  public Genre getGenreId(@RequestParam(value = "id") String id) {
    return genreService.findById(id).orElseThrow(() -> new NotFoundException("Not found entry genre.id: " + id));
  }
}

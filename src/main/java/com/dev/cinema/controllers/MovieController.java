package com.dev.cinema.controllers;

import com.dev.cinema.model.dto.MovieRequestDto;
import com.dev.cinema.model.dto.MovieResponseDto;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.mapper.MovieMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping
    public void create(@RequestBody @Valid MovieRequestDto movieDto) {
        movieService.add(movieMapper.convertToEntity(movieDto));
    }

    @GetMapping
    public List<MovieResponseDto> getAll() {
        return movieService.getAll().stream()
                .map(movieMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }
}

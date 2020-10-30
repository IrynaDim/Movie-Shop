package com.dev.cinema.controllers;

import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.mapper.MovieSessionMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieSessionMapper movieSessionMapper;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping
    public void create(@RequestBody @Valid MovieSessionRequestDto sessionDto) {
        movieSessionService.add(movieSessionMapper.convertToEntity(sessionDto));
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> findAvailableSessions(@RequestParam Long movieId,
                                                               @RequestParam @DateTimeFormat
                                                                       (iso = DateTimeFormat.ISO
                                                                               .DATE)
                                                                       LocalDate date) {
        return movieSessionService.getAvailableSessions(movieId, date)
                .stream().map(movieSessionMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }
}

package com.dev.cinema.controllers;

import com.dev.cinema.controllers.map.MovieSessionMapper;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieSessionMapper movieSessionMapper;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping("/add")
    public void create(@RequestBody MovieSessionRequestDto sessionDto) {
        movieSessionService.add(movieSessionMapper.convertToEntity(sessionDto));
    }

    @GetMapping("/{movieId}&{date}")
    public List<MovieSessionResponseDto> findAvailableSessions(@PathVariable Long movieId,
                                                               @PathVariable String date) {
        return movieSessionService.findAvailableSessions(movieId, LocalDate.parse(date))
                .stream().map(movieSessionMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }
}

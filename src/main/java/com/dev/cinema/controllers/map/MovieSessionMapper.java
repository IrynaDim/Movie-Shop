package com.dev.cinema.controllers.map;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionMapper {
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    public MovieSessionMapper(MovieService movieService, CinemaHallService cinemaHallService) {
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    public MovieSessionResponseDto convertToResponseDto(MovieSession movieSession) {
        MovieSessionResponseDto sessionResponseDto = new MovieSessionResponseDto();
        sessionResponseDto.setId(movieSession.getId());
        sessionResponseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
        sessionResponseDto.setMovieTitle(movieSession.getMovie().getTitle());
        sessionResponseDto.setShowTime(movieSession.getShowTime().toString());
        return sessionResponseDto;
    }

    public MovieSession convertToEntity(MovieSessionRequestDto requestDto) {
        MovieSession movieSession = new MovieSession();
        movieSession.setMovie(movieService.findByTitle(requestDto.getMovieTitle()));
        movieSession.setCinemaHall(cinemaHallService.getById(requestDto.getCinemaHallId()));

        movieSession.setShowTime(LocalDateTime.parse(requestDto.getShowTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        return movieSession;
    }
}

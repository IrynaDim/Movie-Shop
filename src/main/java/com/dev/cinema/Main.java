package com.dev.cinema;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.apache.log4j.Logger;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movie.setDescription("movie about street racing");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);

        Movie movieSecond = new Movie();
        movieSecond.setTitle("Fast and Furious 2");
        movieSecond.setDescription("movie about street racing. second part");
        movieService.add(movieSecond);

        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("the biggest cinema hall");
        cinemaHallService.add(cinemaHall);
        CinemaHall cinemaHallSecond = new CinemaHall();
        cinemaHallSecond.setCapacity(50);
        cinemaHallSecond.setDescription("the smallest cinema hall");
        cinemaHallService.add(cinemaHallSecond);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 00)));
        MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);

        MovieSession movieSessionSecond = new MovieSession();
        movieSessionSecond.setCinemaHall(cinemaHallSecond);
        movieSessionSecond.setMovie(movieSecond);
        movieSessionSecond.setShowTime(LocalDateTime.of(LocalDate.of(2020, 10, 07),
                LocalTime.of(20, 00)));
        movieSessionService.add(movieSessionSecond);

        AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        authenticationService.register("newMail", "1254");
        try {
            authenticationService.login("newMail", "1254");
        } catch (AuthenticationException e) {
            logger.warn("Log-in was failed. " + e);
        }
        try {
            authenticationService.register("newMail", "1234");
        } catch (Exception e) {
            logger.info("Exception is expected: " + e);
        }
        User iryna = new User();
        iryna.setEmail("fff");
        iryna.setPassword("156");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        userService.add(iryna);
        ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        shoppingCartService.registerNewShoppingCart(iryna);
        shoppingCartService.addSession(movieSession, iryna);
        shoppingCartService.addSession(movieSessionSecond, iryna);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCartService.getByUser(iryna).getTickets(), iryna);
        logger.info("Get order by user id: " + shoppingCartService.getByUser(iryna));
        logger.info("Get order by user id: " + orderService.getOrderHistory(iryna));
    }
}

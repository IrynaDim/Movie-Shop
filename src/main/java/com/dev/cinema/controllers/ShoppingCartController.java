package com.dev.cinema.controllers;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.service.mapper.ShoppingCartMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartMapper mapper;
    private final ShoppingCartService cartService;
    private final UserService userService;
    private final MovieSessionService sessionService;

    public ShoppingCartController(ShoppingCartMapper mapper, ShoppingCartService cartService,
                                  UserService userService, MovieSessionService sessionService) {
        this.mapper = mapper;
        this.cartService = cartService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getUserById(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getByEmail(email).get();
        ShoppingCart cart = cartService.getByUser(userService.getByEmail(user.getEmail()).get());
        return mapper.convertToResponseDto(cart);
    }

    @PostMapping("/movie-sessions")
    public void addMovieSession(Authentication authentication, @RequestParam Long sessionId) {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.getByEmail(email).get();
        MovieSession session = sessionService.getById(sessionId);
        cartService.addSession(session, user);
    }
}

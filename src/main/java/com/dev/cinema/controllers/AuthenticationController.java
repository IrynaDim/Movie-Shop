package com.dev.cinema.controllers;

import com.dev.cinema.model.dto.UserRegistrationDto;
import com.dev.cinema.security.AuthenticationService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserRegistrationDto registrationDto) {
        service.register(registrationDto.getEmail(), registrationDto.getPassword());
    }
}

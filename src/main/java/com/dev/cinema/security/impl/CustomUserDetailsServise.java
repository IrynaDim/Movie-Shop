package com.dev.cinema.security.impl;

import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServise implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsServise(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email" + email));
        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(email)
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(r -> r.getRoleName().toString())
                        .toArray(String[]::new));
        return builder.build();
    }
}

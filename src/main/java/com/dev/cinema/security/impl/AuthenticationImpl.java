package com.dev.cinema.security.impl;

import static com.dev.cinema.util.HashUtil.hashPassword;

import com.dev.cinema.exception.AuthenticationException;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationImpl implements AuthenticationService {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public AuthenticationImpl(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.getByEmail(email);
        if (userFromDB.isPresent() && hashPassword(password, userFromDB.get().getSalt())
                .equals(userFromDB.get().getPassword())) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Incorrect login or password.");
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}

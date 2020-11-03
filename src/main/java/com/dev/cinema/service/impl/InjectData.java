package com.dev.cinema.service.impl;

import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InjectData {
    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    public InjectData(UserService userService, RoleService roleService,
                      ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostConstruct
    public void injector() {
        Role adminRole = Role.of("ADMIN");
        Role userRole = Role.of("USER");
        roleService.add(adminRole);
        roleService.add(userRole);

        User user = new User();
        user.setRoles(Set.of(userRole));
        user.setPassword("1111");
        user.setEmail("user1111@gmail.com");

        User admin = new User();
        admin.setRoles(Set.of(adminRole));
        admin.setPassword("0000");
        admin.setEmail("admin1111@gmail.com");

        userService.add(user);
        shoppingCartService.registerNewShoppingCart(user);
        userService.add(admin);
    }
}

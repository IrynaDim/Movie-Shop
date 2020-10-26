package com.dev.cinema.controllers;

import com.dev.cinema.controllers.mapper.OrderMapper;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.OrderRequestDto;
import com.dev.cinema.model.dto.OrderResponseDto;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final ShoppingCartService cartService;

    public OrderController(OrderService orderService, OrderMapper orderMapper,
                           UserService userService, ShoppingCartService cartService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping("/complete")
    public void complete(@RequestBody OrderRequestDto orderDto) {
        User user = userService.getById(orderDto.getUserId());
        ShoppingCart cart = cartService.getByUser(user);
        List<Ticket> ticket = cart.getTickets();
        orderService.completeOrder(ticket, user);
    }

    @GetMapping("/get-order")
    public List<OrderResponseDto> getOrderById(@RequestParam Long userId) {
        return orderService.getOrderHistory(userService.getById(userId))
                .stream().map(orderMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }
}

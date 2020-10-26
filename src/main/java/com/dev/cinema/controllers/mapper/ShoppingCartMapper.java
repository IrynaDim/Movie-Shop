package com.dev.cinema.controllers.mapper;

import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {
    public ShoppingCartResponseDto convertToResponseDto(ShoppingCart order) {
        ShoppingCartResponseDto cartResponseDto = new ShoppingCartResponseDto();
        cartResponseDto.setId(order.getId());
        cartResponseDto.setUserId(order.getUser().getId());
        cartResponseDto.setTicketsId(order.getTickets()
                .stream().map(Ticket::getId)
                .collect(Collectors.toList()));
        return cartResponseDto;
    }
}

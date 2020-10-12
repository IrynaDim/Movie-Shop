package com.dev.cinema.service.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.Orders;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Override
    public Orders completeOrder(List<Ticket> tickets, User user) {
        Orders order = new Orders();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setTickets(tickets);
        return orderDao.create(order);
    }

    @Override
    public List<Orders> getOrderHistory(User user) {
        return orderDao.getOrderHistory(user);
    }

}


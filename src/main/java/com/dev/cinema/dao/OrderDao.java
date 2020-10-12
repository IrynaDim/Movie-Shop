package com.dev.cinema.dao;

import com.dev.cinema.model.Orders;
import com.dev.cinema.model.User;
import java.util.List;

public interface OrderDao {
    public Orders create(Orders order);

    public List<Orders> getOrderHistory(User user);
}

package com.es.core.service.order;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;

import java.util.UUID;

public interface OrderService {

    Order get(Long id);
    Order createOrder(Cart cart);
    void placeOrder(Order order) throws OutOfStockException;

    Order getByUuid(UUID uuid);
}

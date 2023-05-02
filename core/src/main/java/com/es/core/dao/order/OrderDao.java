package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {

    Optional<Order> getById(UUID uuid);

    void save(Order order);

}

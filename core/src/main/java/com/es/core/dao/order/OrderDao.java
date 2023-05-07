package com.es.core.dao.order;

import com.es.core.model.order.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao {

    Optional<Order> getById(Long id);

    void save(Order order);

    Optional<Order> getByUuid(UUID uuid);
}

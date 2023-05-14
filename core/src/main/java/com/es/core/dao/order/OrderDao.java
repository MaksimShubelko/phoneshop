package com.es.core.dao.order;

import com.es.core.model.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {

    void save(Order order);

    List<Order> findAll();
    Optional<Order> findByUuid(UUID uuid);

    Optional<Order> findById(Long serialNo);
}

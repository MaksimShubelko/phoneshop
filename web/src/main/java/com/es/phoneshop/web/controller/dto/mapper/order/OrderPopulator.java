package com.es.phoneshop.web.controller.dto.mapper.order;

import com.es.core.model.order.Order;
import com.es.phoneshop.web.controller.dto.OrderDto;

public interface OrderPopulator {

    void populate(OrderDto dto, Order order);
}

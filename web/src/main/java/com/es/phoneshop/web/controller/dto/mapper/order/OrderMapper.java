package com.es.phoneshop.web.controller.dto.mapper.order;

import com.es.core.model.order.Order;
import com.es.phoneshop.web.controller.dto.OrderDto;

public interface OrderMapper {

    void map(OrderDto dto, Order order);
}

package com.es.phoneshop.web.controller.dto.mapper.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.phoneshop.web.controller.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public void map(OrderDto dto, Order order) {
        if (Objects.isNull(dto) || Objects.isNull(order)) {
            return;
        }

        if (Objects.nonNull(dto.getFirstname())) {
            order.setFirstName(dto.getFirstname());
        }

        if (Objects.nonNull(dto.getLastname())) {
            order.setLastName(dto.getLastname());
        }

        if (Objects.nonNull(dto.getDeliveryAddress())) {
            order.setDeliveryAddress(dto.getDeliveryAddress());
        }

        if (Objects.nonNull(dto.getContactPhoneNo())) {
            order.setContactPhoneNo(dto.getContactPhoneNo());
        }

        if (Objects.nonNull(dto.getAdditionalInf())) {
            order.setAdditionalInf(dto.getAdditionalInf());
        }

        if (Objects.isNull(order.getStatus())) {
            order.setStatus(OrderStatus.NEW.getStatus());
        }
    }
}

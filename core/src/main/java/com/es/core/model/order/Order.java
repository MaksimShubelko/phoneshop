package com.es.core.model.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Order
{
    private UUID id;

    private Long serialNo;

    private List<OrderItem> orderItems = new ArrayList<>();

    private BigDecimal subtotal;

    private BigDecimal deliveryPrice;

    private BigDecimal totalPrice;

    private String firstName;

    private String lastName;

    private String deliveryAddress;

    private String contactPhoneNo;

    private String additionalInf;

    private String status;

}

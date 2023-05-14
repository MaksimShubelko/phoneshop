package com.es.core.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    NEW("New"),
    DELIVERED("Delivered"),
    REJECTED("Rejected"),
    UNDEFINED("undefined");

    private final String status;

}

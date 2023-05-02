package com.es.core.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    NEW("New"),
    DELIVERED("Delivered"),
    REJECTED("Rejected"),
    UNDEFINED("undefined");

    private final String status;

}

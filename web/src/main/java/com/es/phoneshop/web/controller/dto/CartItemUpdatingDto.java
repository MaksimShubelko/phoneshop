package com.es.phoneshop.web.controller.dto;

import java.util.Map;

public class CartItemUpdatingDto {
    private Map<Long, Long> cartItems;

    public Map<Long, Long> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Long, Long> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<Long, Long> get() {
        return cartItems;
    }
}

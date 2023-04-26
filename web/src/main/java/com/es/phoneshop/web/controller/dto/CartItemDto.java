package com.es.phoneshop.web.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class CartItemDto {
    private Long phoneId;

    private String quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

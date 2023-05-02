package com.es.phoneshop.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
public class OrderDto {

    @Pattern(regexp = "^[A-Za-z]{1,32}$", message = "Should contains a-z, A-Z and from 1 to 32 symbols")
    private String firstname;

    @Pattern(regexp = "^[A-Za-z]{1,32}$", message = "Should contains a-z, A-Z and from 1 to 32 symbols")
    private String lastname;

    @Size(max = 256, min = 1, message = "Shouldn't be longer than 256 symbols, shouldn't be empty")
    private String deliveryAddress;

    @Pattern(regexp = "^[0-9]{7,13}$", message = "Shouldn't be shorter than 7 symbols and longer than 13 symbols, may contain only digits")
    private String contactPhoneNo;

    @Size(max = 256, message = "Shouldn't be longer than 256 symbols")
    private String additionalInf;
}

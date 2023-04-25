package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.dto.CartItemDto;
import com.es.phoneshop.web.controller.validator.CartItemDaoValidator;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;
    @Resource
    private CartItemDaoValidator cartItemDaoValidator;
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(cartItemDaoValidator);
    }
    @PostMapping
    public ResponseEntity<?> addPhone(@Valid @RequestBody CartItemDto cartItemDto, BindingResult bindingResult) {
        AjaxResponseBody ajaxResponseBody = new AjaxResponseBody();
        if (!bindingResult.hasErrors()) {
            cartService.addPhone(cartItemDto.getPhoneId(), cartItemDto.getQuantity());
            ajaxResponseBody.setMsg("Successfully added");
            Cart cart = cartService.getCart();
            ajaxResponseBody.setTotalQuantity(cart.getTotalQuantity());
            ajaxResponseBody.setTotalPrice(cart.getTotalPrice());
            return ResponseEntity.ok(ajaxResponseBody);
        } else {
            ajaxResponseBody.setMsg("Quantity should be positive");
            return ResponseEntity.badRequest().body(ajaxResponseBody);
        }
    }

    @ExceptionHandler(InvalidFormatException.class)
    private ResponseEntity<?> invalidFormatExHandle() {
        AjaxResponseBody ajaxResponseBody = new AjaxResponseBody();
        ajaxResponseBody.setMsg("Invalid value");
        return ResponseEntity.badRequest().body(ajaxResponseBody);
    }

    @ExceptionHandler(NumberFormatException.class)
    private ResponseEntity<?> outOfStockExHandle() {
        AjaxResponseBody ajaxResponseBody = new AjaxResponseBody();
        ajaxResponseBody.setMsg("Out of stock");
        return ResponseEntity.badRequest().body(ajaxResponseBody);
    }
}

package com.es.phoneshop.web.controller;

import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.dto.CartItemDto;
import com.es.phoneshop.web.controller.validator.CartItemDaoValidator;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @Resource
    private CartItemDaoValidator cartItemDaoValidator;
    @InitBinder("cartItemDto")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(cartItemDaoValidator);
    }
    @PostMapping
    public Map<String, String> addPhone(@Valid @RequestBody CartItemDto cartItemDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            cartService.addPhone(cartItemDto.getPhoneId(), Long.parseLong(cartItemDto.getQuantity()));
            return Collections.singletonMap("message", "Added");
        } else {
            return Collections.singletonMap("error", bindingResult.getFieldError("quantity").getDefaultMessage());
        }
    }

}

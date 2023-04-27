package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    @GetMapping("/{id}")
    public String getDetails(@PathVariable(name = "id") Long phoneId, Model model) {
        Phone phone = phoneService.getById(phoneId);
        model.addAttribute("phone", phone);
        model.addAttribute("cart", cartService.getCart());

        return "productDetails";
    }
}

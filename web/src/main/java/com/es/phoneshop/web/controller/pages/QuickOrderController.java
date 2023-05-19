package com.es.phoneshop.web.controller.pages;

import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
import com.es.phoneshop.web.controller.dto.QuickOrderItemDto;
import com.es.phoneshop.web.controller.dto.QuickOrderItemsDto;
import com.es.phoneshop.web.controller.validator.AddToCartValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/quickOrder")
@RequiredArgsConstructor
public class QuickOrderController {

    private final CartService cartService;

    private final PhoneService phoneService;

    private final AddToCartValidator addToCartValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addToCartValidator);
    }

    @GetMapping
    public String quickOrder(Model model) {
        model.addAttribute("orderItems", new QuickOrderItemsDto());
        return "quickOrder";
    }

    @PostMapping
    public String quickOrder(@Valid @ModelAttribute(name = "orderItems") QuickOrderItemsDto orderItems,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMsg", "There were errors");
        } else {
            model.addAttribute("successMsg", "All products was added");
        }

        Set<String> addedProducts = new HashSet<>();
        orderItems.getItems().stream()
                .filter(item -> !item.isHasErrors())
                .forEach(item -> {
                    cartService.addPhone(phoneService.findPhoneByModel(item.getPhoneModel()).getId(), item.getQuantity());
                    addedProducts.add(item.getPhoneModel());
                    clearAddedProducts(item);
                });
        model.addAttribute("addedProducts", addedProducts);

        return "quickOrder";
    }

    private void clearAddedProducts(QuickOrderItemDto orderItem) {
        orderItem.setQuantity(null);
        orderItem.setPhoneModel(null);
    }
}

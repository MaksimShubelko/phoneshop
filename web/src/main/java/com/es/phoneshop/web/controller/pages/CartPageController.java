package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.dto.CartItemUpdatingDto;
import com.es.phoneshop.web.controller.validator.CartItemUpdatingDaoValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;
    @Resource
    private CartItemUpdatingDaoValidator cartItemUpdatingDaoValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(cartItemUpdatingDaoValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cartItems", cart.getItems());
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(@Valid @ModelAttribute("cartItemsDto") CartItemUpdatingDto cartItemUpdatingDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Map<Long, Long> items = new HashMap<>(cartItemUpdatingDto.getCartItems());

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            redirectAttributes.addFlashAttribute("errors", errors);
        }

        cartService.update(items);
        redirectAttributes.addFlashAttribute("updated", true);
        return "redirect:/cart";
    }

    @PostMapping("/delete/{id}")
    public String removeItem(@PathVariable(name = "id") Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }
}

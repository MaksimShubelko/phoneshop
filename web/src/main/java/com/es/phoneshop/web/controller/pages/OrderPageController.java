package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.service.cart.CartService;
import com.es.core.service.order.OrderService;
import com.es.phoneshop.web.controller.dto.OrderDto;
import com.es.phoneshop.web.controller.dto.mapper.order.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value = "/order")
public class OrderPageController {

    private OrderService orderService;

    private CartService cartService;

    private OrderMapper orderMapper;
    @GetMapping
    public String getOrder(Model model) {
        Order order = orderService.createOrder(cartService.getCart());
        if (order.getOrderItems().isEmpty()) {
            model.addAttribute("msg", "Cart is empty");
            return "cart";
        }

        model.addAttribute("order", order);
        if (!model.containsAttribute("orderDto")) {
            model.addAttribute("orderDto", new OrderDto());
        }

        return "order";
    }

    @PostMapping
    public String placeOrder(@Valid @ModelAttribute("orderDto") OrderDto orderDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasFieldErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderDto", bindingResult);
            redirectAttributes.addFlashAttribute("orderDto", orderDto);
            return "redirect:/order";
        }

        Order order = orderService.createOrder(cartService.getCart());
        orderMapper.map(orderDto, order);
        orderService.placeOrder(order);

        return "redirect:/orderOverview/" + order.getId();
    }

    @ExceptionHandler(OutOfStockException.class)
    private String outOfStockExHandle(OutOfStockException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("msg", e.getMessage());

        return "redirect:/order";
    }
}

package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    public final OrderService orderService;

    @GetMapping
    public String getOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);

        return "admin";
    }

    @GetMapping("/{id}")
    public String getOrders(@PathVariable(name = "id") Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);

        return "orderOverview";
    }

    @PostMapping("/{id}")
    public String changeStatus(@PathVariable(name = "id") Long id,
                               @RequestParam("status") OrderStatus status) {
        Order order = orderService.findById(id);
        orderService.updateStatus(order, status);

        return "redirect:/admin/orders";
    }
}

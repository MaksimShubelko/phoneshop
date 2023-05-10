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

    @GetMapping("/{serialNo}")
    public String getOrders(@PathVariable(name = "serialNo") Long serialNo, Model model) {
        Order order = orderService.findBySerialNo(serialNo);
        model.addAttribute("order", order);

        return "orderOverview";
    }

    @PostMapping("/{serialNo}")
    public String changeStatus(@PathVariable(name = "serialNo") Long serialNo,
                               @RequestParam("status") OrderStatus status) {
        Order order = orderService.findBySerialNo(serialNo);
        orderService.updateStatus(order, status);

        return "redirect:/admin/orders";
    }
}

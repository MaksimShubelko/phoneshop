package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    private OrderService orderService;

    @GetMapping("/{uuid}")
    public String getOrder(@PathVariable(name = "uuid") UUID uuid, Model model) {
        Order order = orderService.get(uuid);
        model.addAttribute("order", order);

        return "orderOverview";
    }
}

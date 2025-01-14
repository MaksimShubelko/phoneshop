package com.es.phoneshop.web.controller.pages;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/login")
public class LoginPageController {
    @GetMapping
    public String login(@RequestParam(required = false) String error,
                        Authentication authentication,
                        Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/productList";
        }
        if (Objects.nonNull(error)) {
            model.addAttribute("error", "Invalid username or password");
        }

        return "login";
    }
}
package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @GetMapping
    public ModelAndView shop(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("request", request);
        return modelAndView;
    }
}

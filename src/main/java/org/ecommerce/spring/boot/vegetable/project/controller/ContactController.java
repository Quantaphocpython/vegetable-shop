package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @GetMapping
    public ModelAndView contact(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("contact");
        modelAndView.addObject("request", request);
        return modelAndView;
    }
}

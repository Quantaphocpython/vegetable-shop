package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.dto.LMessageDTO;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.utility.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private SendMail sendMail;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView contact(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("contact");
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("request", request);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @PostMapping("/sendMessage")
    public String sendLeaveMessage(@RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String description) throws MessagingException, UnsupportedEncodingException {
        sendMail.sendLeaveMessage(new LMessageDTO(name, email, description));
        return "send message success";
    }
}

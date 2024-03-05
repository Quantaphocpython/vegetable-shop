package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.dto.LMessageDTO;
import org.ecommerce.spring.boot.vegetable.project.utility.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private SendMail sendMail;

    @GetMapping
    public ModelAndView contact(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("contact");
        modelAndView.addObject("request", request);
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

package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.entity.UserOrder;
import org.ecommerce.spring.boot.vegetable.project.service.UserOrderService;
import org.ecommerce.spring.boot.vegetable.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserOrderService userOrderService;
    @Autowired private UserService userService;

    @GetMapping
    public ModelAndView admin(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/getAllOrder")
    public List<UserOrder> getAllOrder() {
        return userOrderService.getAllOrder();
    }

    @GetMapping("/confirm/{orderId}")
    public RedirectView confirmOrder(@PathVariable Long orderId) {
        RedirectView redirectView = new RedirectView("/admin");
        userOrderService.confirmOrder(orderId);
        return redirectView;
    }

    @DeleteMapping("/deleteUser")
    public String deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "delete success";
    }
}

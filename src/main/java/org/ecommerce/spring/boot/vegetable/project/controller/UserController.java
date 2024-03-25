package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.dto.UserDto;
import org.ecommerce.spring.boot.vegetable.project.entity.*;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.UserOrderService;
import org.ecommerce.spring.boot.vegetable.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserOrderService userOrderService;
    @Autowired private UserService userService;
    @Autowired private CategoryService categoryService;

    @GetMapping("/purchase")
    public ModelAndView purchase(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("user");

        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        UserOrder userOrder = userOrderService.findByUserEmail(userEmail);

        System.out.println(userEmail);
        modelAndView.addObject("userOrder", userOrder);
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView account(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("user");

        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userService.findByEmail(userEmail);
        UserDto userDto = UserDto.builder()
                .firstName(user.getFirstName())
                .lastName((user.getLastName()))
                .email(user.getEmail())
                .id(user.getId())
                .build();

        modelAndView.addObject("user", userDto);
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/getAllUserOrder")
    public List<UserOrder>  getAllUserOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        List<UserOrder> userOrders = userOrderService.getAllUserOrder(userEmail);
        return userOrders;
    }

    @PatchMapping("/changeName")
    public RedirectView changeNameUser(@ModelAttribute UserDto user) {
        RedirectView redirectView = new RedirectView("/user/profile");
        userService.changeNameUser(user);
        return redirectView;
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "delete success";
    }
}

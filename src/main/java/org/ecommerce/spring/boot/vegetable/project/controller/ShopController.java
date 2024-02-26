package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView shop(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("shop");
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", request);
        return modelAndView;
    }
    @GetMapping("/getProductSaleOffList/{categoryName}")
    public List<Product> getProductSaleOffList(@PathVariable String categoryName) {
        return productService.getProductSaleOffList(categoryName);
    }
}

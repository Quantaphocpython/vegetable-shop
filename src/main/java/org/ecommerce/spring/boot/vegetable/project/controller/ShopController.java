package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.OrderItem;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/product/{id}")
    public ModelAndView productDetail(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("productDetail");
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("shoppingCart");
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", request);
        return modelAndView;

    }

    @GetMapping("/getProductSaleOffList/{categoryName}")
    public List<Product> getProductSaleOffList(@PathVariable String categoryName) {
        return productService.getProductSaleOffList(categoryName);
    }

    @GetMapping("/getProductList/{pageNumber}")
    public List<Product> getProductList(@PathVariable Integer pageNumber,
                                        @RequestParam Integer min,
                                        @RequestParam Integer max,
                                        @RequestParam String categoryName,
                                        @RequestParam String sort) {
        return productService.getProductList(pageNumber, 12, min, max, categoryName, sort);
    }

    @GetMapping("/getProductSize")
    public Long getProductSize(@RequestParam double min,
                               @RequestParam double max,
                               @RequestParam String categoryName) {
        return productService.getProductSize(min, max, categoryName);
    }

    @GetMapping("/getTotalPages")
    public Long getTotalPages(@RequestParam double min, @RequestParam double max, @RequestParam String categoryName) {
        return productService.getTotalPagesAll(min, max, 12, categoryName);
    }

    @GetMapping("/getLatestProduct")
    public List<Product> getLatestProduct() {
        return productService.getLatestProduct();
    }

    @GetMapping("/goShopCategory")
    public ModelAndView goShopCategory(@RequestParam Integer number) {
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("number", number);
        return modelAndView;
    }

    @GetMapping("/productDetail/{id}")
    public Product getProductDetail(@PathVariable Long id) {
        Product product = productService.findProductById(id);
        return product;
    }

    @GetMapping("/getProductByCategory")
    public List<Product> getProductByCategory(@RequestParam Long categoryId,
                                              @RequestParam Long productId) {
        List<Product> products = productService.getProductByCategory(categoryId, productId);
        return products;
    }

    @GetMapping("/order")
    public String order(@RequestParam Long productId,
                        @RequestParam Integer quantity,
                        @RequestParam Long userId) {
        return productService.order(productId, quantity, userId);
    }

}

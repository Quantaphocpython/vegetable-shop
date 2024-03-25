package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.dto.OrderInformation;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.OrderItem;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.ProductService;
import org.ecommerce.spring.boot.vegetable.project.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired private CategoryService categoryService;
    @Autowired private ProductService productService;
    @Autowired private UserOrderService userOrderService;

    @GetMapping
    public ModelAndView shop(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("shop");
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String search, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("shop");
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", request);
        modelAndView.addObject("search", search);
        return modelAndView;
    }

//    @GetMapping("searchProduct")
//    public List<Product> searchProducts(@RequestParam String search) {
//        List<Product> products = productService.searchProducts(search);
//    }

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

    @GetMapping("/checkout")
    public ModelAndView checkout(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("checkout");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        modelAndView.addObject("request", request);
        modelAndView.addObject("orderInformation", new OrderInformation());
        modelAndView.addObject("totalPrice", userOrderService.getTotalPrice(userEmail));
        modelAndView.addObject("orderItems", userOrderService.getOrderItemList(userEmail));
        return modelAndView;
    }

    @GetMapping("/getOrderItemList")
    public List<OrderItem> getOrderItemList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<OrderItem> userOrderItems =  userOrderService.getOrderItemList(userName);
        return userOrderItems;
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

    @PostMapping("/order")
    public RedirectView order(@RequestParam Long productId,
                              @RequestParam Integer quantity,
                              @RequestParam Long userId,
                              RedirectAttributes attributes) {
//        RedirectView  modelAndView = new RedirectView ("redirect:/shop/product/" + productId);
        RedirectView  modelAndView = new RedirectView ("/shop/product/" + productId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String add = productService.order(productId, quantity, authentication.getName());
        attributes.addFlashAttribute("add", add);
//        modelAndView.
        return modelAndView;
    }

    @DeleteMapping("/deleteOrderItemById")
    public Boolean deleteOrderItemById(@RequestParam Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return userOrderService.deleteOrderItemById(id, userEmail);
    }

    @GetMapping("/getTotalPrice")
    public double getTotalPrice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return userOrderService.getTotalPrice(userEmail);
    }

    @PatchMapping("/changeQuantity")
    public Boolean changeQuantity(@RequestParam Long id, @RequestParam int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return userOrderService.changeQuantity(id, quantity, userEmail);
    }

    @PatchMapping("/order")
    public RedirectView finishOrder(@ModelAttribute OrderInformation o, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/home");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        userOrderService.addOrderInformation(o, userEmail);
        attributes.addFlashAttribute("addInfor", "success");
        return redirectView;
    }

}

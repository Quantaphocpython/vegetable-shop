package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.dto.BlogDto;
import org.ecommerce.spring.boot.vegetable.project.dto.ProductDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Banner;
import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;
import org.ecommerce.spring.boot.vegetable.project.service.BannerService;
import org.ecommerce.spring.boot.vegetable.project.service.BlogService;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping({"/", "/home"})
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ModelAndView home(HttpServletRequest request) throws ParseException {
        ModelAndView modelAndView = new ModelAndView("home");
        List<Category> categories = categoryService.getCategoryList();
        List<Banner> banners = bannerService.getBannerList();
        List<Blog> blogs = blogService.getBlogList("ALL", 0);
        blogs = blogs.subList(blogs.size() - 3, blogs.size());
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("banners", banners);
        modelAndView.addObject("blogs", blogs);
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login");
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestParam("name") String name, @RequestParam("image") MultipartFile file) throws IOException {
        categoryService.addCategory(name, file);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/getCategoryList")
    public ModelAndView getCategoryList() {
        ModelAndView modelAndView = new ModelAndView("home");
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/getCategoryImage/{id}")
    public ResponseEntity<?> getCategoryImage(@PathVariable Long id) {
        byte[] image = categoryService.getCategoryImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        Product p = productService.addProduct(productDto);
        return ResponseEntity.ok("add product success");
    }

    @GetMapping("/getProductList/{pageNumber}")
    public List<Product> getProductList(@PathVariable Integer pageNumber) {
        List<Product> products = productService.getProductList(pageNumber, 8, 1, 1000, "ALL", "id-inc");
        return products;
    }

    @GetMapping("/getProductImage/{id}")
    public ResponseEntity<?> getProductImage(@PathVariable Long id) {
        byte[] image = productService.getProductImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    @GetMapping("/getProductListByCategory/{categoryName}/{pageNumber}")
    @ResponseBody
    public List<Product> getProductListByCategory(@PathVariable String categoryName, @PathVariable Integer pageNumber) {
        List<Product> products = productService.getProductListByCategory(categoryName, pageNumber);
        return products;
    }

    @GetMapping("/getTotalPages/{categoryName}")
    @ResponseBody
    public Long getTotalPages(@PathVariable String categoryName) {
        long totalPage = productService.getTotalPages(categoryName);
        return totalPage;
    }

    @GetMapping("/getTotalPagesAll")
    @ResponseBody
    public Long getTotalPagesAll() {
        long totalPage = productService.getTotalPagesAll(1, 1000, 8, "ALL");
        return totalPage;
    }

    @PostMapping("/addBanner")
    public ResponseEntity<?> addBanner(@RequestParam String name, @RequestParam MultipartFile image) throws IOException {
        bannerService.addBanner(name, image);
        return ResponseEntity.ok("add banner successs");
    }

    @GetMapping("/getBannerList")
    public ModelAndView getBannerList() {
        ModelAndView modelAndView = new ModelAndView("home");
        List<Banner> banners = bannerService.getBannerList();
        modelAndView.addObject("banners", banners);
        return modelAndView;
    }

    @GetMapping("/getBannerImage/{id}")
    public ResponseEntity<?> getBannerImageById(@PathVariable Long id) {
        byte[] image = bannerService.getBannerImageById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

    @PostMapping("/addBlog")
    public ResponseEntity<?> addBlog(@ModelAttribute BlogDto blogDto) throws IOException {
        blogService.addBlog(blogDto);
        return ResponseEntity.ok("add blog success");
    }

    @GetMapping("/getBlogImage/{id}")
    public ResponseEntity<?> getBlogImageById(@PathVariable Long id) {
        byte[] image = blogService.getBlogImageById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }


}





















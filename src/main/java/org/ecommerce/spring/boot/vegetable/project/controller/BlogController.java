package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.ecommerce.spring.boot.vegetable.project.entity.BlogCategory;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.service.BlogCategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.BlogService;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogCategoryService blogCategoryService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView blog(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("blog");
        List<String> blogCategories = blogCategoryService.getBlogCategory();
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("blogCategories", blogCategories);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/blogDetail")
    public ModelAndView  blogDetail(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("blogDetail");
        List<String> blogCategories = blogCategoryService.getBlogCategory();
        List<Category> categories = categoryService.getCategoryList();
        modelAndView.addObject("blogCategories", blogCategories);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @GetMapping("/getBlogList")
    public List<Blog> getBlogList(@RequestParam("blogCategoryName") String blogCategoryName,
                                  @RequestParam("pageNumber") Integer pageNumber) {
        return blogService.getBlogList(blogCategoryName, pageNumber);
    }

    @GetMapping("/getRecentNews")
    public List<Blog> getRecentNews() {
        return blogService.getRecentNews();
    }

    @GetMapping("/getTotalPages")
    public Long getTotalPages(@RequestParam String blogCategoryName) {
        return blogService.getTotalPages(blogCategoryName);
    }

}

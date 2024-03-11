package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.ecommerce.spring.boot.vegetable.project.entity.BlogCategory;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.service.BlogCategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.BlogService;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.UserService;
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

    @Autowired
    private UserService userService;

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

    @GetMapping("/findBlogById")
    public Blog findBlogById(@RequestParam Integer blogId) {
        Blog blog = blogService.findBlogById(blogId);
        return blog;
    }

    @GetMapping("/findUserById")
    public User findUserById(@RequestParam Long id) {
        User user = userService.findUserById(id);
        return user;
    }

    @GetMapping("/searchBlog")
    public List<Blog> searchBlogByTitle(@RequestParam String blogTitle,
                                        @RequestParam Integer pageNumber) {
        List<Blog> blogs = blogService.searchBlogByTitle(blogTitle, pageNumber);
        return blogs;
    }

    @GetMapping("/getBlogSearchToTalPages")
    public Long getBlogSearchToTalPages(@RequestParam String blogTitle) {
        return blogService.getBlogSearchToTalPages(blogTitle);
    }

    @GetMapping("/getBlogByBlogCategories")
    public List<Blog> getBlogByBlogCategories(@RequestParam Long blogId) {
        List<Blog> blogs = blogService.getBlogByBlogCategories(blogId);
        return blogs;
    }

}

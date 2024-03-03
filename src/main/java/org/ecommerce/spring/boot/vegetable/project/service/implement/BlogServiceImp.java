package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.dto.BlogDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.ecommerce.spring.boot.vegetable.project.entity.BlogCategory;
import org.ecommerce.spring.boot.vegetable.project.repository.BlogCategoryRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.BlogRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.UserRepository;
import org.ecommerce.spring.boot.vegetable.project.service.BlogCategoryService;
import org.ecommerce.spring.boot.vegetable.project.service.BlogService;
import org.ecommerce.spring.boot.vegetable.project.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImp implements BlogService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Override
    public void addBlog(BlogDto blogDto) throws IOException {
        Blog blog = Blog.builder()
                .title(blogDto.getTitle())
                .content(blogDto.getContent())
                .image(imageUtils.compressImage(blogDto.getImage().getBytes()))
                .user(userRepository.findById(blogDto.getUserId()).get())
                .blogCategories(blogCategoryChangeList(blogDto.getBlogCategory()))
                .build();
        blogRepository.save(blog);
    }

    @Override
    public List<Blog> getBlogList(String blogCategoryName, Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 6, Sort.by(Sort.Direction.DESC, "id"));
        if(blogCategoryName.equals("ALL")) {
            return blogRepository.findAll(page).getContent();
        }
        BlogCategory blogCategory = blogCategoryRepository.findByName(blogCategoryName);
        return blogRepository.findByBlogCategoriesIn(blogCategory.getId(), page).getContent();
    }

    @Override
    public byte[] getBlogImageById(Long id) {
        Blog blog = blogRepository.findById(id).get();
        return imageUtils.decompressImage(blog.getImage());
    }

    @Override
    public List<Blog> getRecentNews() {
        Pageable page = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "id"));
        return blogRepository.findAll(page).getContent();
    }

    @Override
    public Long getTotalPages(String blogCategoryName) {
        Pageable page = PageRequest.of(0, 6);
        if(blogCategoryName.equals("ALL")) {
            return (long) blogRepository.findAll(page).getTotalPages();
        }
        BlogCategory blogCategory = blogCategoryRepository.findByName(blogCategoryName);
        return (long) blogRepository.findByBlogCategoriesIn(blogCategory.getId(), page).getTotalPages();
    }

    private List<BlogCategory> blogCategoryChangeList(String blogCategoryString) {
        List<String> blogCategoryNames = List.of(blogCategoryString.split(","));
        List<BlogCategory> blogCategories = new ArrayList<>();
        for(String blogCategoryName : blogCategoryNames) {
            blogCategoryName = blogCategoryName.trim();
            System.out.println(blogCategoryName + "\n");
            BlogCategory blogCategory = blogCategoryRepository.findByName(blogCategoryName);
            if(blogCategory != null) {
                blogCategories.add(blogCategory);
            }
            else {
                blogCategories.add(new BlogCategory(blogCategoryName));
            }
        }
        return blogCategories;
    }
}

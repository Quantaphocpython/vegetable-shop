package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.dto.BlogDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.ecommerce.spring.boot.vegetable.project.entity.BlogCategory;
import org.ecommerce.spring.boot.vegetable.project.repository.BlogRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.UserRepository;
import org.ecommerce.spring.boot.vegetable.project.service.BlogService;
import org.ecommerce.spring.boot.vegetable.project.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImp implements BlogService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private BlogRepository blogRepository;

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
    public List<Blog> getBlogList() {
        List<Blog> blogs = blogRepository.findTop3ByOrderByIdDesc();
        return blogs;
    }

    @Override
    public byte[] getBlogImageById(Long id) {
        Blog blog = blogRepository.findById(id).get();
        return imageUtils.decompressImage(blog.getImage());
    }

    private List<BlogCategory> blogCategoryChangeList(String blogCategoryString) {
        List<BlogCategory> blogCategories =
                Arrays.asList(blogCategoryString.split(",")).stream().map(categoryName -> new BlogCategory(categoryName.trim()))
                        .collect(Collectors.toList());
        return blogCategories;
    }
}

package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.dto.BlogDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.ecommerce.spring.boot.vegetable.project.entity.BlogCategory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface BlogService {
    void addBlog(BlogDto blogDto) throws IOException;

    List<Blog> getBlogList(String blogCategoryName, Integer pageNumber);

    byte[] getBlogImageById(Long id);

    List<Blog> getRecentNews();

    Long getTotalPages(String blogCategoryName);
}

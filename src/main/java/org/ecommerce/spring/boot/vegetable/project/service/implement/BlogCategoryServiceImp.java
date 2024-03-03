package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.entity.BlogCategory;
import org.ecommerce.spring.boot.vegetable.project.repository.BlogCategoryRepository;
import org.ecommerce.spring.boot.vegetable.project.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogCategoryServiceImp implements BlogCategoryService {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Override
    public List<String> getBlogCategory() {
        List<BlogCategory> blogCategories = blogCategoryRepository.findAll();
        List<String> blogCategoryNames = new ArrayList<>();
        for(BlogCategory blogCategory : blogCategories) {
            if(!blogCategoryNames.contains(blogCategory.getName())) {
                blogCategoryNames.add(blogCategory.getName());
            }
        }
        return blogCategoryNames;
    }
}

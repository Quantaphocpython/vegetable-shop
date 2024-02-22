package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.repository.CategoryRepository;
import org.ecommerce.spring.boot.vegetable.project.service.CategoryService;
import org.ecommerce.spring.boot.vegetable.project.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void addCategory(String name, MultipartFile file) throws IOException {
            Category category = Category.builder()
                .name(name)
                .image(imageUtils.compressImage(file.getBytes()))
                .build();
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        for(Category c : categories) {
            c.setImage(imageUtils.decompressImage(c.getImage()));
        }
        return categories;
    }

    @Override
    public byte[] getCategoryImage(Long id) {
        Category category = categoryRepository.findById(id).get();
        return imageUtils.decompressImage(category.getImage());
    }
}

package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    void addCategory(String name, MultipartFile file) throws IOException;

    List<Category> getCategoryList();

    byte[] getCategoryImage(Long id);
}

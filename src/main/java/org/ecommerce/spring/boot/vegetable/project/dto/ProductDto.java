package org.ecommerce.spring.boot.vegetable.project.dto;

import lombok.Data;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private String name;
    private double cost;
    private MultipartFile image;
    private String categoryName;
    private String description;
}

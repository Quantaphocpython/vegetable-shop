package org.ecommerce.spring.boot.vegetable.project.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BlogDto {
    private String title;
    private String content;
    private MultipartFile image;
    private Long userId;
    private String blogCategory;
}

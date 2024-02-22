package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    void addBanner(String name, MultipartFile image) throws IOException;

    List<Banner> getBannerList();

    byte[] getBannerImageById(Long id);
}

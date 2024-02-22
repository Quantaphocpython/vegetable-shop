package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.entity.Banner;
import org.ecommerce.spring.boot.vegetable.project.repository.BannerRepository;
import org.ecommerce.spring.boot.vegetable.project.service.BannerService;
import org.ecommerce.spring.boot.vegetable.project.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BannerServiceImp implements BannerService {

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public void addBanner(String name, MultipartFile image) throws IOException {
        Banner banner = Banner.builder()
                .name(name)
                .image(imageUtils.compressImage(image.getBytes()))
                .build();
        bannerRepository.save(banner);
    }

    @Override
    public List<Banner> getBannerList() {
        List<Banner> banners = bannerRepository.findTop2ByOrderByIdDesc();
        return banners;
    }

    @Override
    public byte[] getBannerImageById(Long id) {
        Banner banner = bannerRepository.findById(id).get();
        return imageUtils.decompressImage(banner.getImage());
    }


}

package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.dto.ProductDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;
import org.ecommerce.spring.boot.vegetable.project.repository.CategoryRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.ProductRepository;
import org.ecommerce.spring.boot.vegetable.project.service.ProductService;
import org.ecommerce.spring.boot.vegetable.project.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product addProduct(ProductDto productDto) throws IOException {
        Product product = Product.builder()
                .name(productDto.getName())
                .cost(productDto.getCost())
                .description(productDto.getDescription())
                .image(imageUtils.compressImage(productDto.getImage().getBytes()))
                .category(categoryRepository.getByName(productDto.getCategoryName()))
                .build();
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProductList(Integer pageNumber) {
        return productRepository.findAll(PageRequest.of(pageNumber, 8)).getContent();
    }

    @Override
    public byte[] getProductImage(Long id) {
        return
                imageUtils.decompressImage(
                        productRepository.findById(id).get().getImage()
                );
    }

    @Override
    public List<Product> getProductListByCategory(String categoryName, Integer pageNumber) {
        Category category = categoryRepository.getByName(categoryName);
        Pageable page = PageRequest.of(pageNumber, 8);
        return productRepository.getProductListByCategory(category, page).getContent();
    }

    @Override
    public Long getTotalPages(String categoryName) {
        Category category = categoryRepository.getByName(categoryName);
        Long totalPage = (long) productRepository.findAllByCategory(category, PageRequest.of(0, 8)).getTotalPages();
        return totalPage;
    }

    @Override
    public long getTotalPagesAll() {
        Long totalPage = (long) productRepository.findAll(PageRequest.of(0, 8)).getTotalPages();
        return totalPage;
    }

    @Override
    public List<Product> getProductSaleOffList(String categoryName) {
        if(categoryName.equals("ALL")) {
            return productRepository.findAllByIsSaleTrue();
        }
        Category category = categoryRepository.getByName(categoryName);
        return productRepository.findAllByIsSaleTrueAndCategory(category);
    }
}

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
import org.springframework.data.domain.Sort;
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
    public List<Product> getProductList(Integer pageNumber, Integer pageSize, Integer min,
                                        Integer max, String categoryName, String sort) {
        Sort sorting = Sort.by("id");
        if (sort.equals("price-dec")) {
            sorting = Sort.by(Sort.Direction.DESC, "cost");
        }
        if(categoryName.equals("ALL")) {
            return productRepository.findAllByCostBetween(min, max, PageRequest.of(pageNumber, pageSize, sorting)).getContent();
        }
        Category category = categoryRepository.getByName(categoryName);
        return productRepository.findAllByCostBetweenAndCategory(min, max, category,PageRequest.of(pageNumber, pageSize, sorting)).getContent();
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
    public long getTotalPagesAll(double min, double max, Integer pageSize, String categoryName) {
        if(categoryName.equals("ALL")) {
            return (long) productRepository.findAllByCostBetween(min, max, PageRequest.of(0, pageSize)).getTotalPages();
        }
        Category category = categoryRepository.getByName(categoryName);
        Long totalPage = (long) productRepository.findAllByCostBetweenAndCategory(min, max, category, PageRequest.of(0, pageSize)).getTotalPages();
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

    @Override
    public Long getProductSize(double min, double max, String categoryName) {
        if(categoryName.equals("ALL"))
            return productRepository.countByCostBetween(min, max);
        Category category = categoryRepository.getByName(categoryName);
        return productRepository.countByCostBetweenAndCategory(min, max, category);
    }

    @Override
    public List<Product> getLatestProduct() {
        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by("id").descending());

        return productRepository.findAll(pageRequest).getContent();
    }
}

package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.dto.ProductDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto) throws IOException;

    List<Product> getProductList(Integer pageNumber, Integer pageSize, Integer min, Integer max, String categoryName, String sort);

    byte[] getProductImage(Long id);

    List<Product> getProductListByCategory(String categoryName, Integer pageNumber);

    Long getTotalPages(String categoryName);

    long getTotalPagesAll(double min, double max, Integer pageSize, String categoryName);

    List<Product> getProductSaleOffList(String categoryName);

    Long getProductSize(double min, double max, String categoryName);

    List<Product> getLatestProduct();
}

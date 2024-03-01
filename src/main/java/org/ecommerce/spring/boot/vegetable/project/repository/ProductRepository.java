package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;
import org.ecommerce.spring.boot.vegetable.project.service.ProductService;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> getProductListByCategory(Category category, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);

    List<Product> findAllByIsSaleTrue();

    List<Product> findAllByIsSaleTrueAndCategory(Category category);
    Long countByCostBetween(double min, double max);

    Page<Product> findAllByCostBetween(double min, double max, Pageable pageable);

    Page<Product> findAllByCostBetweenAndCategory(double min, double max, Category category, Pageable pageable);

    Long countByCostBetweenAndCategory(double min, double max, Category category);
}

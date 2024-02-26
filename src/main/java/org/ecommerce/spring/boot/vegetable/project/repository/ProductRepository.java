package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.ecommerce.spring.boot.vegetable.project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> getProductListByCategory(Category category, Pageable pageable);

    Page<Product> findAllByCategory(Category category, Pageable pageable);

    List<Product> findAllByIsSaleTrue();

    List<Product> findAllByIsSaleTrueAndCategory(Category category);
}

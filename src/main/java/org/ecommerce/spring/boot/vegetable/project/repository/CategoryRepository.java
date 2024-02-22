package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getByName(String categoryName);
}

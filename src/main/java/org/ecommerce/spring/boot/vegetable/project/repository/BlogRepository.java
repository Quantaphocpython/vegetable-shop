package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findTop3ByOrderByIdDesc();
}

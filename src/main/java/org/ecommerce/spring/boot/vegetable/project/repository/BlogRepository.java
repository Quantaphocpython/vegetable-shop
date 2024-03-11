package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.Blog;
import org.ecommerce.spring.boot.vegetable.project.entity.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findTop3ByOrderByIdDesc();

    @Query(
            value = "SELECT * FROM blog " +
                    "WHERE id IN " +
                    "(SELECT blog_id FROM blog_categories " +
                    "WHERE blog_category_id = :blogCategoryId)",
            nativeQuery = true)
    Page<Blog> findByBlogCategoriesIn(@Param("blogCategoryId") Long blogCategoryId, Pageable page);

    @Query("SELECT b FROM Blog b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :blogTitle, '%'))")
    Page<Blog> searchBlogByTitle(@Param("blogTitle") String blogTitle, Pageable pageable);


    @Query(value = "SELECT * " +
            "FROM Blog " +
            "WHERE " +
            "    id IN ( " +
            "        SELECT bc.blog_id " +
            "        FROM blog_categories bc " +
            "        WHERE bc.blog_category_id IN ( " +
            "            SELECT bc2.blog_category_id " +
            "            FROM blog_categories bc2 " +
            "            WHERE bc2.blog_id = :blogId " +
            "        ) " +
            "    ) " +
            "    AND id <> :blogId", nativeQuery = true)
//    @Query(value = "SELECT * from blog", nativeQuery = true)
    Page<Blog> getBlogByBlogCategories(@Param("blogId") Long blogId, Pageable page);
}

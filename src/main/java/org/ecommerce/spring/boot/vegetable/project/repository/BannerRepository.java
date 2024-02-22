package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findTop2ByOrderByIdDesc();
}

package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
            value = "SELECT * FROM Order where status = 'not yet' AND user_id = :userId",
            nativeQuery = true
    )
    Order findOrderByUserIdAndStatusNotYet(@Param("userId") Long userId);
}

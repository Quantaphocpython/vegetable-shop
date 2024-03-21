package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.entity.UserOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

    @Query(
            value = "SELECT * FROM user_order where status = 'not yet' AND user_id = :userId",
            nativeQuery = true
    )
    UserOrder findOrderByUserIdAndStatusNotYet(@Param("userId") Long userId);

    UserOrder findByUser(User user);


    Page<UserOrder> findAllByUser(User user, Pageable page);
}

package org.ecommerce.spring.boot.vegetable.project.repository;

import jakarta.transaction.Transactional;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteByEnabled(Boolean enabled);

    @Query(value = "DELETE FROM user_roles WHERE user_id = :id", nativeQuery = true)
    @Modifying
    void delete1(@Param("id") Long id);
}

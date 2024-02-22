package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

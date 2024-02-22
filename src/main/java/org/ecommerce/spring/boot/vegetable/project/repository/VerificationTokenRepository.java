package org.ecommerce.spring.boot.vegetable.project.repository;

import org.ecommerce.spring.boot.vegetable.project.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}

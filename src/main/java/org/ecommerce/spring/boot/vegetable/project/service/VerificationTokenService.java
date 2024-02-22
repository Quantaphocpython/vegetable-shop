package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.entity.VerificationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface VerificationTokenService {
    void saveVerificationTokenForUser(User user, String token);

    Optional<VerificationToken> findByToken(String token);

    String validateVerificationToken(String token);

}

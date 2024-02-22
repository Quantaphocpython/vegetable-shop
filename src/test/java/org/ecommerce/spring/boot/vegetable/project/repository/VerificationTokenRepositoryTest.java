package org.ecommerce.spring.boot.vegetable.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VerificationTokenRepositoryTest {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Test
    public void deleteVerificationTokenTest() {
        verificationTokenRepository.deleteById(2L);
    }

}
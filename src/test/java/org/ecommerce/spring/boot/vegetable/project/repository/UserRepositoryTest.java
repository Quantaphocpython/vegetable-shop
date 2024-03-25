package org.ecommerce.spring.boot.vegetable.project.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.ecommerce.spring.boot.vegetable.project.entity.Role;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository repository;
    private EntityManager entityManager;


    @Test
    public void delete1() {
        userRepository.delete1(16L);
    }

}
package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.dto.UserDto;
import org.ecommerce.spring.boot.vegetable.project.entity.User;

public interface UserService {
    User registerUser(UserDto userDto);

    User findUserById(Long id);

    User findByEmail(String userEmail);

    void changeNameUser(UserDto user);

    void deleteUserById(Long id);
}

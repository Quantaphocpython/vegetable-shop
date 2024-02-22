package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.dto.UserDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Role;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.repository.UserRepository;
import org.ecommerce.spring.boot.vegetable.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Arrays.asList(new Role("USER")))
                .enabled(false)
                .build();
        return userRepository.save(user);
    }
}

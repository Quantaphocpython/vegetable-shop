package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.dto.UserDto;
import org.ecommerce.spring.boot.vegetable.project.entity.Role;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.repository.RoleRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.UserRepository;
import org.ecommerce.spring.boot.vegetable.project.service.UserService;
import org.ecommerce.spring.boot.vegetable.project.utility.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private ImageUtils imageUtils;
    @Autowired private RoleRepository roleRepository;
    @Override
    public User registerUser(UserDto userDto) {
        Role role = roleRepository.findByName("USER");
        if(role == null) {
            role = new Role("USER");
        }
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Arrays.asList(role))
                .enabled(false)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }

    @Override
    public User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).get();
    }

    @Override
    public void changeNameUser(UserDto user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setFirstName(user.getFirstName());
        user1.setLastName((user.getLastName()));
        userRepository.save(user1);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}

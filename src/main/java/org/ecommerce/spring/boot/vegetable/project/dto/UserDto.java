package org.ecommerce.spring.boot.vegetable.project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Confirm password should not be empty")
    private String confirmPassword;
}

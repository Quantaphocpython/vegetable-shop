package org.ecommerce.spring.boot.vegetable.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LMessageDTO {
    private String name;
    private String email;
    private String description;
}

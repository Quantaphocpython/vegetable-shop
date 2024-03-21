package org.ecommerce.spring.boot.vegetable.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderInformation {
    private String address;
    private String phoneNumber;
    private String orderNote;
}

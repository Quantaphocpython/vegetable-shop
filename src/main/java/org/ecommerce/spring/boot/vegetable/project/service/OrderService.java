package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.entity.Order;

public interface OrderService {
    Order findOderNotYetByUserId(Long userId);
}

package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.entity.Order;
import org.ecommerce.spring.boot.vegetable.project.repository.OrderRepository;
import org.ecommerce.spring.boot.vegetable.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findOderNotYetByUserId(Long userId) {
        Order order = orderRepository.findOrderByUserIdAndStatusNotYet(userId);
        return order;
    }
}

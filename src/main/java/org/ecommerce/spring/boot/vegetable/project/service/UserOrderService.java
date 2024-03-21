package org.ecommerce.spring.boot.vegetable.project.service;

import org.ecommerce.spring.boot.vegetable.project.dto.OrderInformation;
import org.ecommerce.spring.boot.vegetable.project.entity.OrderItem;
import org.ecommerce.spring.boot.vegetable.project.entity.UserOrder;

import java.util.List;

public interface UserOrderService {
    UserOrder findOderNotYetByUserId(Long userId);

    List<OrderItem> getOrderItemList(String userName);

    Boolean deleteOrderItemById(Long id, String userEmail);

    double getTotalPrice(String userEmail);

    Boolean changeQuantity(Long id, int quantity, String userEmail);

    void addOrderInformation(OrderInformation o, String userEmail);

    UserOrder findByUserEmail(String userEmail);

    List<UserOrder> getAllUserOrder(String userEmail);
}

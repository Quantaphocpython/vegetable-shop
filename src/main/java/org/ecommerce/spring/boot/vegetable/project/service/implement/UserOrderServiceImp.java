package org.ecommerce.spring.boot.vegetable.project.service.implement;

import org.ecommerce.spring.boot.vegetable.project.dto.OrderInformation;
import org.ecommerce.spring.boot.vegetable.project.entity.OrderItem;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.entity.UserOrder;
import org.ecommerce.spring.boot.vegetable.project.repository.OrderItemRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.UserOrderRepository;
import org.ecommerce.spring.boot.vegetable.project.repository.UserRepository;
import org.ecommerce.spring.boot.vegetable.project.service.UserOrderService;
import org.ecommerce.spring.boot.vegetable.project.service.UserService;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class UserOrderServiceImp implements UserOrderService {

    @Autowired private UserOrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public UserOrder findOderNotYetByUserId(Long userId) {
        UserOrder order = orderRepository.findOrderByUserIdAndStatusNotYet(userId);
        return order;
    }

    @Override
    public List<OrderItem> getOrderItemList(String userName) {
        User user = userRepository.findByEmail(userName).get();
        UserOrder userOrder = orderRepository.findOrderByUserIdAndStatusNotYet(user.getId());
        return userOrder.getOrderItems();
    }

    @Override
    public Boolean deleteOrderItemById(Long id, String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        UserOrder userOrder = orderRepository.findOrderByUserIdAndStatusNotYet(user.getId());

        Iterator<OrderItem> iterator = userOrder.getOrderItems().iterator();
        while (iterator.hasNext()) {
            OrderItem orderItem = iterator.next();
            if (orderItem.getId().equals(id)) {
                iterator.remove();
                orderRepository.save(userOrder);
                orderItemRepository.deleteById(id);
                return true;
            }
        }

        return false;
    }

    @Override
    public double getTotalPrice(String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        UserOrder userOrder = orderRepository.findOrderByUserIdAndStatusNotYet(user.getId());
        List<OrderItem> orderItems = userOrder.getOrderItems();
        double price = 0;
        for(OrderItem o : orderItems) {
            double cost;
            if(o.getProductOrder().getIsSale() == true) {
                cost = o.getProductOrder().getSaleCost();
            } else {
                cost = o.getProductOrder().getCost();
            }
            price += o.getQuantity() * cost;
        }
        return price;
    }

    @Override
    public Boolean changeQuantity(Long id, int quantity, String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        UserOrder userOrder = orderRepository.findOrderByUserIdAndStatusNotYet(user.getId());
        List<OrderItem> orderItems = userOrder.getOrderItems();
        for(OrderItem o : orderItems) {
            if(o.getId() == id) {
                o.setQuantity(quantity);
                orderItemRepository.save(o);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addOrderInformation(OrderInformation o, String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        UserOrder userOrder = orderRepository.findOrderByUserIdAndStatusNotYet(user.getId());
        userOrder.setAddress(o.getAddress());
        userOrder.setPhoneNumber(o.getPhoneNumber());
        userOrder.setOrderNote(o.getOrderNote());
        userOrder.setStatus("in progress");
        orderRepository.save(userOrder);
    }

    @Override
    public UserOrder findByUserEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        return orderRepository.findOrderByUserIdAndStatusNotYet(user.getId());
    }

    @Override
    public List<UserOrder> getAllUserOrder(String userEmail) {
        Pageable page = PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "id"));
        User user = userRepository.findByEmail(userEmail).get();
        List<UserOrder> userOrders = orderRepository.findAllByUser(user, page).getContent();
        System.out.println(userOrders);
        return userOrders;
    }

}

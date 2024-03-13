package org.ecommerce.spring.boot.vegetable.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "id"
    )
    private List<OrderItem> orderItems;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }
}
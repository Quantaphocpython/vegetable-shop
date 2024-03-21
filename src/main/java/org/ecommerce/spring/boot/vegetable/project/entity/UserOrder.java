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
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "order_item_mapping",
            joinColumns = @JoinColumn(
                    name = "user_order_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "order_item_id",
                    referencedColumnName = "id"
            )
    )
    private List<OrderItem> orderItems;
    private String address;
    private String phoneNumber;
    private String orderNote;

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
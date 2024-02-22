package org.ecommerce.spring.boot.vegetable.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double cost;
    private String description;
    @Lob
    @Column(length = 1000000000)
    private byte[] image;

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    private Category category;

}

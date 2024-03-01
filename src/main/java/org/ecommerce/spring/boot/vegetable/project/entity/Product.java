package org.ecommerce.spring.boot.vegetable.project.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @NonNull
    private Boolean isSale;
    private Integer salePercent;
    @Transient
    private Double saleCost;

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    private Category category;

    @PostLoad
    public void createSaleCost() {
        if(isSale) {
            saleCost = (double) cost * (100 - salePercent) / 100;
        }
    }
}

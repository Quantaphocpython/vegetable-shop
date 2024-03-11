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

    @Lob
    @Column(length = 1000000000)
    private String description;

    @Lob
    @Column(length = 1000000000)
    private byte[] image;

    @NonNull
    private Boolean isSale;
    private Integer salePercent;
    @Transient
    private Double saleCost;

    @Transient
    private Double starAverage;

    @Transient
    private Integer totalReviews;

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
    )
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "review_id",
            referencedColumnName = "id"
    )
    private Review review;

    @PostLoad
    public void createSaleCostAndStarAverageAndTotalReviews() {
        if(isSale) {
            saleCost = (double) cost * (100 - salePercent) / 100;
        } else {
            saleCost = (double) Double.MAX_VALUE;
        }

        if(review == null) {
            starAverage = (double) 0;
        } else {
            starAverage = review.getStarAverage();
        }

        if(review == null) {
            totalReviews = 0;
        } else {
            totalReviews = review.getTotalReviews();
        }
    }
}

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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer oneStar;
    private Integer twoStar;
    private Integer threeStar;
    private Integer fourStar;
    private Integer fiveStar;

    @Lob
    @Column(length = 1000000000)
    private String message;

    @Transient
    private Double starAverage;

    @Transient
    private Integer totalReviews;

    @PostLoad
    private void setStarAverageAndTotalReviews() {
        totalReviews = oneStar + twoStar + threeStar + fiveStar + fiveStar;
        starAverage = ((double) (oneStar) + (2 * twoStar) + (3 * threeStar) + (4 * fourStar) + (5 * fiveStar)) / totalReviews;
    }
}

package org.ecommerce.spring.boot.vegetable.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @DateTimeFormat(pattern = "MM-dd, YYYY")
    private Date createDate;

    @Lob
    @Column(length = 1000000000)
    private String content;

    @Lob
    @Column(length = 1000000000)
    private byte[] image;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "blog_categories",
            joinColumns = @JoinColumn(
                    name = "blog_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "blogCategory_id",
                    referencedColumnName = "id"
            )
    )
    private List<BlogCategory> blogCategories;

    @PrePersist
    private void onCreate() {
        createDate = new Date();
    }
}

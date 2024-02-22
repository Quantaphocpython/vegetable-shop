package org.ecommerce.spring.boot.vegetable.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.spring.boot.vegetable.project.utility.TokenExpirationTimeCalculus;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expirationTime = TokenExpirationTimeCalculus.getExpirationTime();
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = TokenExpirationTimeCalculus.getExpirationTime();
    }
}

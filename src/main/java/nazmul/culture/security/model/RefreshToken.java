package nazmul.culture.security.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import nazmul.culture.security.model.User;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "culture_refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

}

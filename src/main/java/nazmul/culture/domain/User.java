package nazmul.culture.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "culture_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(
        name = "venue_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "venue_id")
    )
    @JsonBackReference
    private Set<Venue> venueLiked = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = false, mappedBy = "user")
    @JsonBackReference // prevent back reference
    @EqualsAndHashCode.Exclude //
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "culture_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonBackReference // prevent back reference
    private Set<Role> roles;




}

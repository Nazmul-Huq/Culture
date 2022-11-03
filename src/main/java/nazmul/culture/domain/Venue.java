package nazmul.culture.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import nazmul.culture.security.model.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "culture_venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "venueLiked")
    @JsonBackReference
    private Set<User> userLikes = new HashSet<>();
}


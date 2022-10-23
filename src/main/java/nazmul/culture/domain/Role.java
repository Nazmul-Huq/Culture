package nazmul.culture.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "culture_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String description;
}

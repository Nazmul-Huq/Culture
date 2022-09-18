package nazmul.culture.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "culture_event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String venue;

    @ManyToOne
    @JsonBackReference // prevent back reference
    @EqualsAndHashCode.Exclude //
    private Band band;

    @OneToMany(mappedBy = "event")
    @JsonBackReference // prevent back reference
    @EqualsAndHashCode.Exclude //
    private List<Review> reviews = new ArrayList<>();


}

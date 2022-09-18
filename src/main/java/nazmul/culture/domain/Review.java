package nazmul.culture.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "culture_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private int rating;

    @ManyToOne
    @JsonBackReference // prevent back reference
    @EqualsAndHashCode.Exclude //
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JsonBackReference // prevent back reference
    @EqualsAndHashCode.Exclude //
    @JoinColumn(name = "event_id")
    private Event event;

    }

package nazmul.culture.repository;

import nazmul.culture.domain.Event;
import nazmul.culture.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByEvent(Event event);

}

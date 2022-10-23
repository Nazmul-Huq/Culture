package nazmul.culture.repository;

import nazmul.culture.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrderByTimestamp();
}

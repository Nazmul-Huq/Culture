package nazmul.culture.repository;

import nazmul.culture.domain.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Long> {
}

package nazmul.culture.repository;

import nazmul.culture.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);

    Optional<Object> findByUsername(String username);
}

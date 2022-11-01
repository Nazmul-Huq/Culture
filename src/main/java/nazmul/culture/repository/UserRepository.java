package nazmul.culture.repository;

import nazmul.culture.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT FROM User u where name like %?1%")
    List<User> findByNameContaining(String name);

    User findByUsername(String username);
}

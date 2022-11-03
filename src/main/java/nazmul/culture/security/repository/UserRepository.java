package nazmul.culture.security.repository;

import nazmul.culture.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //@Query("SELECT FROM User u where name like %?1%")
    List<User> findByNameContaining(String name);

    User findByUsername(String username);
}

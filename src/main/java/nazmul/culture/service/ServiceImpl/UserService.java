package nazmul.culture.service.ServiceImpl;

import nazmul.culture.domain.User;
import nazmul.culture.repository.UserRepository;
import nazmul.culture.service.IService.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }
}
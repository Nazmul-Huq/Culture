package nazmul.culture.security.service;

import nazmul.culture.security.config.SecurityConfiguration;
import nazmul.culture.security.model.User;
import nazmul.culture.security.repository.UserRepository;
import nazmul.culture.security.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        PasswordEncoder pw = SecurityConfiguration.passwordEncoder();
        user.setPassword(pw.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    @Override
    public User findByUsername(String username) {

        User user = userRepository.findByUsername(username);
        return user;
    }
}

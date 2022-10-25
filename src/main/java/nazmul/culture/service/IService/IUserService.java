package nazmul.culture.service.IService;

import nazmul.culture.domain.User;
import nazmul.culture.service.IService.IGlobalService;

import java.util.List;

public interface IUserService extends IGlobalService<User, Long> {
    public List<User> findByName(String name);
    public User findByUsername(String username);
}

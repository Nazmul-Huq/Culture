package nazmul.culture.service.ServiceImpl;

import nazmul.culture.domain.Role;
import nazmul.culture.repository.RoleRepository;
import nazmul.culture.service.IService.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

    }
}

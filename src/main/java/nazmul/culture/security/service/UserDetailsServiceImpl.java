package nazmul.culture.security.service;

import lombok.AllArgsConstructor;
import nazmul.culture.security.model.User;
import nazmul.culture.security.model.UserDetailsImpl;
import nazmul.culture.security.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(" UserDetailsServiceImpl loadUserByUsername Call: 5,6");
        User user = userService.findByUsername(username);
        System.out.println("users from database: " + user.getName());
        if(user!=null) {
            System.out.println("found the user in Database: " + user.getName());
            return UserDetailsImpl.buildUserDetail(user);
        }else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}

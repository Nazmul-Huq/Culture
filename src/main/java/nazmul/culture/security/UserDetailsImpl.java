package nazmul.culture.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nazmul.culture.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    public static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String name, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl buildUserDetail(User user){
        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map( role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

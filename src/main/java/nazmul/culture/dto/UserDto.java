package nazmul.culture.dto;

import lombok.Data;
import nazmul.culture.domain.Role;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String username;
    private String password;
    private Set<String> roles = new HashSet<>();
}

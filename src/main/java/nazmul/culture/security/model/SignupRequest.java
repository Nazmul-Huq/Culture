package nazmul.culture.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String roles;
}

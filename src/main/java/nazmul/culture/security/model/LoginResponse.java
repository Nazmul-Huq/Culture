package nazmul.culture.security.model;

import lombok.*;
import nazmul.culture.security.model.RefreshToken;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private final String token;
    private final RefreshToken refreshToken;
}

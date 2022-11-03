package nazmul.culture.dto;

import lombok.*;
import nazmul.culture.domain.RefreshToken;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private final String token;
    private final RefreshToken refreshToken;
}

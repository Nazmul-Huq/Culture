package nazmul.culture.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse {
    private String token;
    //private RefreshToken refreshToken;
}

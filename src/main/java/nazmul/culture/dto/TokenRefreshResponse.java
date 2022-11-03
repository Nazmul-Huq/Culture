package nazmul.culture.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nazmul.culture.domain.RefreshToken;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse {
    private String token;
    //private RefreshToken refreshToken;
}

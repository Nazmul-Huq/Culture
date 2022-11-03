package nazmul.culture.security.model;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;

}

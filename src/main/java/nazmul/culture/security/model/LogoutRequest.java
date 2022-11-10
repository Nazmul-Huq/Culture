package nazmul.culture.security.model;

import lombok.Data;

@Data
public class LogoutRequest {
    private String accessToken;
    private String refreshToken;
}

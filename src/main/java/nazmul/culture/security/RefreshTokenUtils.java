package nazmul.culture.security;


import nazmul.culture.security.model.RefreshToken;
import nazmul.culture.security.exception.TokenRefreshException;
import nazmul.culture.security.service.IRefreshTokenService;
import nazmul.culture.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class RefreshTokenUtils {

    @Autowired
    IRefreshTokenService refreshTokenService;
    @Autowired
    IUserService userService;

    public static final long REFRESH_TOKEN_VALIDITY = 2 * 60 * 1000; // 5 min


    public RefreshToken generateRefreshToken(Long userId){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser( (userService.findById(userId)).get());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY));
        refreshToken = refreshTokenService.save(refreshToken);
        return refreshToken;
    } //generateRefreshToken() ends here

    public RefreshToken verifyRefreshTokenValidity(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){
            refreshTokenService.deleteById(refreshToken.getId());
            //refreshTokenService.deleteByUser(refreshToken.getUser());
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired.");
        }
        return refreshToken;
    }

}

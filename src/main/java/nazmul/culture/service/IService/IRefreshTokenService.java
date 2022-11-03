package nazmul.culture.service.IService;

import nazmul.culture.domain.RefreshToken;
import nazmul.culture.domain.User;

import java.util.Optional;

public interface IRefreshTokenService extends IGlobalService<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}

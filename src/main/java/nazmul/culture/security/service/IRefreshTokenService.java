package nazmul.culture.security.service;

import nazmul.culture.security.model.RefreshToken;
import nazmul.culture.security.model.User;
import nazmul.culture.service.IService.IGlobalService;

import java.util.Optional;

public interface IRefreshTokenService extends IGlobalService<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}

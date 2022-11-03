package nazmul.culture.security.service;

import nazmul.culture.security.model.RefreshToken;
import nazmul.culture.security.model.User;
import nazmul.culture.security.repository.RefreshTokenRepository;
import nazmul.culture.security.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public List<RefreshToken> findAll() {
        return null;
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findById(Long id) {
        return refreshTokenRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        refreshTokenRepository.deleteById(id);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}

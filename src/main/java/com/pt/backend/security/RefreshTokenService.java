package com.pt.backend.security;


import com.pt.backend.domain.RefreshToken;
import com.pt.backend.domain.User;
import com.pt.backend.dto.auth.RefreshRequest;
import com.pt.backend.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken createRefreshToken(User user) {

        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .user(user)
                .build();

        return repository.save(token);
    }

    public RefreshToken verifyToken(RefreshRequest request) {
        RefreshToken token = repository
                .findByToken(request.refreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        return token;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }

    public void delete(RefreshToken token) {
        repository.delete(token);
    }

}